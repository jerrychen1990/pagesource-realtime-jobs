package com.youzan.bigdata.streaming.nsq;

import com.youzan.nsq.client.Consumer;
import com.youzan.nsq.client.ConsumerImplV2;
import com.youzan.nsq.client.MessageHandler;
import com.youzan.nsq.client.entity.NSQConfig;
import com.youzan.nsq.client.entity.NSQMessage;
import com.youzan.nsq.client.exception.NSQException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jerry on 11/8/17.
 */
public class NSQConsumer {

    public void consumeUsingSimpleConsumer(String lookUps, String consumerName, String topic) throws NSQException {

        final NSQConfig config = new NSQConfig();
        config.setLookupAddresses(lookUps);              // 必选项. 设置Lookupd(MetaData)集群(多)地址, 是以","分隔的字符串,就是说可以配置一个集群里的多个节点
        //        每个Lookup使用HTTP的端口. Address包含: IP + Port
        config.setConsumerName(consumerName);          // 必选项. 独立Consumer的标识,命名合法字符[.a-zA-Z0-9_-] . 实际等价于NSQ里Channel概念

        config.setConnectTimeoutInMillisecond(10000);  // 可选项. TCP Connect Timeout
        config.setThreadPoolSize4IO(1);  // 可选项. Thread Pool Size with Netty-IO. 建议值:1
        config.setRdy(1);                              // 可选项. 设置Consumer每次被NSQ-Server推过来的消息数量


        final AtomicInteger received = new AtomicInteger(0);
        final Consumer consumer = new ConsumerImplV2(config, new MessageHandler() {
            @Override
            public void process(NSQMessage message) { // Delegate it to SDK working thread-pool
                // 消息已经保证了送到Client并且已经传递给了Client
                received.incrementAndGet();
                System.out.println(message.getReadableContent());


            }
        });
        consumer.setAutoFinish(true); // 可选项. 默认是在Handler里正常处理OK后 就AutoFinish
        consumer.subscribe(topic);   // 必选项. 可以多次调用
        consumer.start();
        // Client做下优雅的关闭,通常是在进程退出前做关闭
//        consumer.close();
//        System.out.println(String.format("Consumer received {} messages.", received.get()));

    }

    public static void main(String[] args) {
        NSQConsumer nsqConsumer = new NSQConsumer();
//        Properties props = new Properties();
//        InputStream in = NSQConsumer.class.getResourceAsStream("../../../../../dev.properties");
//
//        try {
//            props.load(in);
//        } catch (IOException e) {
//            try {
//                in.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }

        if (args.length != 2){
            System.out.println("invalid args number!");
            return;
        }

        String lookups = args[0];
        String channel = "test-nsq-consumer";
        String topic = args[1];

        try {
            nsqConsumer.consumeUsingSimpleConsumer(lookups, channel, topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
