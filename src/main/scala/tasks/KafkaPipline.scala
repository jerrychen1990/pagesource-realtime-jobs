package tasks

import java.io.FileInputStream
import java.util
import java.util.Properties

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.typesafe.scalalogging.slf4j.LazyLogging
import kafka.KafkaSink
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZKStringSerializer, ZkUtils}
import order.Order
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.dstream.{InputDStream}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by jerry on 12/20/17.
  */
class KafkaPipline {

}

object KafkaPipline extends LazyLogging {
    var consumerGroup: String = ""
    var zkClient: ZkClient = null
    val whitelistKdtIdList: util.List[Long] = new util.ArrayList[Long]
    val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.topic, mmd.message())
    var ssc: StreamingContext = null
    var kafkaParams: Map[String, String] = Map()


    def main(args: Array[String]): Unit = {
        if (args.length < 1) {
            System.err.println(s"""|usage: KafkaPipline xxx.properties""".stripMargin)
            System.exit(1)
        }
        val prop = new Properties()
        prop.load(new FileInputStream(args(0)));
        val sparkConf = new SparkConf()
            .setIfMissing("spark.master", "local[4]")
            .setAppName(prop.getProperty("app.name"))
        ssc = new StreamingContext(sparkConf, Seconds(prop.getProperty("batch.interval").toLong))


        //初始化全局变量
        consumerGroup = prop.getProperty("kafka.consumer.group.id")
        zkClient = new ZkClient(prop.getProperty("kafka.zk.list"), Integer.MAX_VALUE, Integer.MAX_VALUE, ZKStringSerializer)
        val whiteListArr = prop.getProperty("whitelist.kdtid").split(",")
        for (str <- whiteListArr) {
            whitelistKdtIdList.add(java.lang.Long.valueOf(str))
        }

        kafkaParams = Map[String, String]("metadata.broker.list" -> prop.getProperty("kafka.bootstrap.servers"))

        val kafkaProducer: Broadcast[KafkaSink[String, String]] = {
            val kafkaProducerConfig = {
                val p = new Properties()
                p.setProperty("bootstrap.servers", prop.getProperty("kafka.bootstrap.servers"))
                p.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
                p.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
                p
            }
            logger.info("kafka producer init done!")
            ssc.sparkContext.broadcast(KafkaSink[String, String](kafkaProducerConfig))
        }

        val srcTopicArr = prop.getProperty("kafka.src.topic").split(",")
        val targetTopicArr = prop.getProperty("kafka.target.topic").split(",")
        for (i <- 0 until Math.min(srcTopicArr.length, targetTopicArr.length)) {
            val srcTopic = srcTopicArr(i)
            val targetTopic = targetTopicArr(i)
            logger.info("mapping topic {} to topic {}", srcTopic, targetTopic)
            val topicDirs: ZKGroupTopicDirs = new ZKGroupTopicDirs(consumerGroup, srcTopic)
            val messages = getDstream(srcTopic, topicDirs)
            doRecordOffsets(messages = messages, topic = srcTopic, topicDirs = topicDirs)
            doPipline(srcTopic = srcTopic, targetTopic = targetTopic, messages = messages, kafkaProducer = kafkaProducer)
        }

        ssc.start()
        ssc.awaitTermination()
    }

    def getDstream(topic: String, topicDirs: ZKGroupTopicDirs): InputDStream[(String, String)] = {
        val children = zkClient.countChildren(topicDirs.consumerOffsetDir)
        var fromOffsets: Map[TopicAndPartition, Long] = Map()
        if (children > 0) {
            //如果zk保存了offset,使用之前保存的offset
            for (i <- 0 until children) {
                val partitionOffset = zkClient.readData[String](s"${topicDirs.consumerOffsetDir}/${i}")
                val tp = TopicAndPartition(topic, i)
                fromOffsets += (tp -> partitionOffset.toLong)
            }
            return KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](
                ssc, kafkaParams, fromOffsets, messageHandler)
        }
        else {
            val topicSet = Set(topic)
            return KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicSet)
        }
    }

    def doRecordOffsets(topic: String, messages: InputDStream[(String, String)], topicDirs: ZKGroupTopicDirs) = {
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
    }

    def doPipline(srcTopic: String, targetTopic: String, messages: InputDStream[(String, String)],
                  kafkaProducer: Broadcast[KafkaSink[String, String]]) = {
        messages.foreachRDD { rdd =>
            rdd.foreachPartition { partitionOfRecords => {
                partitionOfRecords.toList.map(message => {
                    val content = message._2
                    logger.debug("content:{}", content)
                    val order: Order = JSON.parseObject(content, classOf[Order])
                    logger.debug("order:{}", order)
                    order
                }).filter(order => {
                    whitelistKdtIdList.contains(order.getKdtId)
                }).foreach(order => {
                    logger.info("route from topic:{} to topic:{} with order: {}", srcTopic, targetTopic, order)
                    kafkaProducer.value.send(targetTopic, targetTopic, JSON.toJSONString(order, SerializerFeature.SortField))
                });
            }
            }
        }
    }


}
