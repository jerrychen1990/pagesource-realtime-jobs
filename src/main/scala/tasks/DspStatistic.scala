package tasks

import java.io.FileInputStream
import java.util.{Arrays, Properties}

import com.alibaba.fastjson.JSON
import com.github.nezha.httpfetch.{HttpApiConfiguration, HttpApiService, SourceReader, XmlReader}
import com.typesafe.scalalogging.slf4j.LazyLogging
import dsp.DspStatisticBiz
import http.DspPlatformApi
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZKStringSerializer, ZkUtils}
import order.Order
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

/**
  * Created by jerry on 12/15/17.
  */
class DspStatistic {

}

object DspStatistic extends LazyLogging {
    def main(args: Array[String]): Unit = {
        if (args.length < 1) {
            System.err.println(s"""|usage: DspStatistic xxx.properties""".stripMargin)
            System.exit(1)
        }
        val prop = new Properties()

        prop.load(new FileInputStream(args(0)));

        val sparkConf = new SparkConf()
            .setIfMissing("spark.master", "local[4]")
            .setAppName(prop.getProperty("app.name"))

        val ssc = new StreamingContext(sparkConf, Seconds(prop.getProperty("batch.interval").toLong))


        //记录消费组的zk路径
        val topicDirs = new ZKGroupTopicDirs(prop.getProperty("kafka.consumer.group.id"), prop.getProperty("kafka.topic"))
        val zkClient = new ZkClient(prop.getProperty("kafka.zk.list"), Integer.MAX_VALUE, Integer.MAX_VALUE, ZKStringSerializer)
        val children = zkClient.countChildren(topicDirs.consumerOffsetDir)

        var messages: InputDStream[(String, String)] = null;
        val kafkaParams = Map[String, String]("metadata.broker.list" -> prop.getProperty("kafka.bootstrap.servers"))
        val topicsSet = prop.getProperty("kafka.topic").split(",").toSet
        if (children > 0) {
            //如果zk保存了offset,使用之前保存的offset
            var fromOffsets: Map[TopicAndPartition, Long] = Map()
            for (i <- 0 until children) {
                val partitionOffset = zkClient.readData[String](s"${topicDirs.consumerOffsetDir}/${i}")
                val tp = TopicAndPartition(prop.getProperty("kafka.topic"), i)
                fromOffsets += (tp -> partitionOffset.toLong)
            }

            val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.topic, mmd.message())
            messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](
                ssc, kafkaParams, fromOffsets, messageHandler
            )
        }
        else {
            //使用最新的kafka topic中的offset
            messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
        }

        //保存offset到zk
        var offsetRanges = Array[OffsetRange]()
        messages.transform {
            rdd => offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
                rdd
        }.foreachRDD { rdd =>
            for (o <- offsetRanges) {
                val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
                ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)

                logger.info("{} partition: {} offset: {}", o.topic.toString, o.partition.toString, o.fromOffset.toString)
            }
        }



        val host = prop.getProperty("dsp.platform.host")
        val port = prop.getProperty("dsp.platform.port")


        //开始业务逻辑
        messages.foreachRDD { rdd =>
            rdd.foreachPartition { partitionOfRecords => {
                val xmlReader: SourceReader = new XmlReader(Arrays.asList("http-api.xml"))
                val configuration: HttpApiConfiguration = new HttpApiConfiguration
                configuration.setSourceReaders(Arrays.asList(xmlReader))
                configuration.init()
                val service = new HttpApiService(configuration)
                service.init()
                val api: DspPlatformApi = service.getOrCreateService(classOf[DspPlatformApi])
                val dspStatisticBiz: DspStatisticBiz = new DspStatisticBiz(api)
                dspStatisticBiz.DSP_PLATFORM_HOST = host
                dspStatisticBiz.DSP_PLATFORM_PORT = port

                partitionOfRecords.toList.map(message => {
                    val content = message._2
                    logger.debug("content:{}", content)
                    val order: Order = JSON.parseObject(content, classOf[Order])
                    logger.debug("order:{}", order)
                    order
                }).filter(order => {
                    order.getDcps != 0
                }).foreach(order=>{
                    logger.info("update order {}", order)
                    if (!dspStatisticBiz.updateOrders(Arrays.asList(order))) {
                        logger.error("order list update failed!")
                    }
                });
            }
            }
        }
        ssc.start()
        ssc.awaitTermination()
    }

}
