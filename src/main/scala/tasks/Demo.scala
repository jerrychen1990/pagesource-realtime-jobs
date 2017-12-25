package tasks

import java.io.FileInputStream
import java.util
import java.util.{Arrays, Properties}

import com.alibaba.fastjson.JSON
import com.github.nezha.httpfetch.{HttpApiService, HttpApiConfiguration, XmlReader, SourceReader}
import com.typesafe.scalalogging.slf4j.LazyLogging
import commision.CommisionBiz
import http.{DemoApi, DspPlatformApi}
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZkUtils, ZKStringSerializer, ZKGroupTopicDirs}
import order.Order
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, OffsetRange, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import collection.JavaConverters._

import tasks.OrderCommision._

/**
  * Created by jerry on 12/25/17.
  */
class Demo {

}

object Demo extends LazyLogging {
    def main(args: Array[String]): Unit = {
        if (args.length < 1) {
            System.err.println(s"""|usage: Demo xxx.properties""".stripMargin)
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


        val url = prop.getProperty("url");

        //开始业务逻辑
        messages.foreachRDD { rdd =>
            rdd.foreachPartition { partitionOfRecords => {
                val xmlReader: SourceReader = new XmlReader(Arrays.asList("http-api.xml"))
                val configuration: HttpApiConfiguration = new HttpApiConfiguration
                configuration.setSourceReaders(Arrays.asList(xmlReader))
                configuration.init()
                val service = new HttpApiService(configuration)
                service.init()
                val api: DemoApi = service.getOrCreateService(classOf[DemoApi])

                partitionOfRecords.filterNot(message => {
                    message._2.stripMargin.isEmpty
                }).foreach(message => {
                    try {
                        val content = message._2
                        logger.info("content:{}", content)
                        val str = api.getHtml(url, content)
                        logger.info("http response :{}", str)
                    } catch {
                        case e: Exception => {
                            logger.error("error stack:", e)
                            logger.error("error message:{}", message)
                        }
                    }

                })
            }
            }
        }


        ssc.start()
        ssc.awaitTermination()
    }

}