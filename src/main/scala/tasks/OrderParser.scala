package tasks

import java.io.{FileInputStream}
import java.util.Properties


import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import kafka.KafkaSink
import org.apache.spark.broadcast.Broadcast
import parser._

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.spark.streaming.nsq.NSQInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}


/**
  * Created by jerry on 10/30/17.
  */
class OrderParser {

}


object OrderParser extends LazyLogging {
    def main(args: Array[String]): Unit = {
        if (args.length < 1) {
            System.err.println(s"""|usage: orderParser xxx.properties""".stripMargin)
            System.exit(1)
        }
        val prop = new Properties()

        prop.load(new FileInputStream(args(0)));


        val sparkConf = new SparkConf()
            .setIfMissing("spark.master", "local[4]")
            .setAppName(prop.getProperty("app.name"))
            .set("spark.streaming.backpressure.enabled", prop.getProperty("spark.streaming.backpressure.enabled"))
            .set("spark.streaming.receiver.writeAheadLog.enable", prop.getProperty("spark.streaming.receiver.writeAheadLog.enable"))

        val ssc = new StreamingContext(sparkConf, Seconds(prop.getProperty("batch.interval").toLong))

        // 广播KafkaSink
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
        val targetTopic = prop.getProperty("kafka.target_topic")

        val nsqTopic = prop.getProperty("nsq.topic")
        var parser: IOrderParser = null
        if (nsqTopic.equals("trade_order_bill_create")) {
            parser = new OrderCreateParserV1()
        } else if (nsqTopic.equals("ntc_order_create")) {
            parser = new OrderCreateParserV2()
        } else if (nsqTopic.equals("ntc_order_success")) {
            parser = new OrderFinishParserV2()
        } else {
            logger.error("invalid ndq nsq topic:{}", nsqTopic)
            System.exit(1)
        }
        ssc.sparkContext.broadcast(parser)

        new NSQInputDStream(ssc, ssc.sparkContext.getConf, prop).foreachRDD { rdd =>
            rdd.foreachPartition { partitionOfRecords => {
                partitionOfRecords.foreach(message => {
                    val rdmsg = message.getMessage().getReadableContent()
                    logger.debug("get message:{}", rdmsg)
                    try {
                        val Json_msg = JSON.parseObject(rdmsg)
                        val o = parser.parse(Json_msg)
                        val json_str = JSON.toJSONString(o, SerializerFeature.SortField)
                        if(!o.isValid){
                            throw new Exception("invalid order message!")
                        }
                        kafkaProducer.value.send(targetTopic, targetTopic, json_str)
                        val orderNo = o.getInfo()
                        logger.debug("order {} parsed", orderNo)
                    } catch {
                        case e: Exception => {
                            logger.error("error:{}\n with msg:{}", e, rdmsg)
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
