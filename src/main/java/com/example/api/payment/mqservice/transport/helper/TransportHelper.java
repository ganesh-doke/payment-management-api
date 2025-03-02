package com.example.api.payment.mqservice.transport.helper;

import lombok.extern.slf4j.Slf4j;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@Slf4j
public class TransportHelper {

    public byte[] getBytesFromMessage(BytesMessage bytesMessage)
            throws JMSException {

        byte[] bytes;
        int downloadedSize;
        int size = 2048;

        do {
            size = size * 2;
            bytes = new byte[size];
            downloadedSize = bytesMessage.readBytes(bytes);
            bytesMessage.reset();
        } while (downloadedSize == bytes.length);

        return bytes;
    }

    public byte[] getBytesFromMessage(TextMessage message)
            throws JMSException {

        return message.getText().getBytes();
    }

    public byte[] getBytesFromMessage(ObjectMessage message)
            throws JMSException {

        return (byte[]) message.getObject();
    }
}
