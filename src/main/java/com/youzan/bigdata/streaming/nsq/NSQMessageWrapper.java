package com.youzan.bigdata.streaming.nsq;

import com.youzan.nsq.client.entity.Address;
import com.youzan.nsq.client.entity.NSQMessage;
import com.youzan.nsq.client.entity.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static com.youzan.bigdata.streaming.utils.Utils.intToBytes;
import static com.youzan.bigdata.streaming.utils.Utils.longToBytes;


/**
 * Created by chenjunzou on 2017/3/21.
 */

public class NSQMessageWrapper implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(NSQMessageWrapper.class);

    transient private NSQMessage message;

    public NSQMessageWrapper(NSQMessage message) {
        this.message = message;
    }

    public NSQMessage getMessage() {
        return message;
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        byte[] timestamp, attempts, messageID, messageBody, topicName;
        long internalID, traceID, diskQueueOffset;
        int connectionID, diskQueueDataSize, nextConsumingInSecond;
        boolean isExt;
        Address address = null;
        timestamp = new byte[s.readInt()];

        s.read(timestamp);
        attempts = new byte[s.readInt()];
        s.read(attempts);
        messageID = new byte[s.readInt()];
        s.read(messageID);
        internalID = s.readLong();
        traceID = s.readLong();
        messageBody = new byte[s.readInt()];
        s.readFully(messageBody);
        address = (Address) s.readObject();
        connectionID = s.readInt();
        diskQueueOffset = s.readLong();
        diskQueueDataSize = s.readInt();
        nextConsumingInSecond = s.readInt();

        topicName = new byte[s.readInt()];
        s.read(topicName);
        String topicNameStr = new String(topicName);
        isExt = s.readBoolean();

        Topic topic = new Topic(topicNameStr);
        message = new NSQMessage(timestamp, attempts, messageID,
                longToBytes(internalID), longToBytes(traceID), messageBody,
                address, connectionID, nextConsumingInSecond, topic, isExt);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(message.getTimestamp().length);
        s.write(message.getTimestamp());
        s.writeInt(message.getAttempts().length);
        s.write(message.getAttempts());
        s.writeInt(message.getMessageID().length);
        s.write(message.getMessageID());
        s.writeLong(message.getInternalID());
        s.writeLong(message.getTraceID());
        s.writeInt(message.getMessageBody().length);
        s.write(message.getMessageBody());
        s.writeObject(message.getAddress());
        s.writeInt(message.getConnectionID());
        s.writeLong(message.getDiskQueueOffset());
        s.writeInt(message.getDiskQueueDataSize());
        s.writeInt(message.getNextConsumingInSecond());
        s.writeInt(message.getTopicInfo().getTopicName().length());
        s.write(message.getTopicInfo().getTopicName().getBytes());
        s.writeBoolean(message.getTopicInfo().isExt());

    }
}
