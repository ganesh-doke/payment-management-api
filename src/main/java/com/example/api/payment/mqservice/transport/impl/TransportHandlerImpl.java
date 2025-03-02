package com.example.api.payment.mqservice.transport.impl;

import com.example.api.payment.mqservice.transport.TransportHandler;
import com.example.api.payment.mqservice.transport.error.TransportException;
import com.example.api.payment.mqservice.transport.error.TransportTimeoutException;
import com.example.api.payment.mqservice.transport.helper.TransportHelper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.jms.*;

@Builder
@Slf4j
public class TransportHandlerImpl implements TransportHandler {

    private final QueueConnectionFactory queueConnectionFactory;
    private final Queue requestQueue;
    private final Queue responseQueue;
    private QueueSession queueSession;
    private QueueConnection queueConnection;
    private String correlationId;

    @Value("${api.mq.jms.transaction.name}")
    private String transactionName;

    @Value("${api.mq.jms.message.type}")
    private String messageType;

    @Value("${api.mq.jms.routing.key}")
    private String routingKey;

    @Override
    public void close() throws TransportException {

        try {
            if (this.queueSession != null) {
                this.queueSession.close();
                this.queueSession = null;
            }
            if (this.queueConnection != null) {
                this.queueConnection.close();
                this.queueConnection = null;
            }
        } catch (JMSException e) {
            log.error("Error closing queue connection", e);
        }
    }

    private void closeAll(AutoCloseable autoCloseable) {
        try {
            if (autoCloseable != null) {
                autoCloseable.close();
            }
            this.close();
        } catch (Exception e) {
            log.error("Error closing queue connection", e);
        }
    }

    private QueueSession getQueueSession() {

        try {
            if (queueSession == null) {
                queueConnection = getQueueConnection();
                queueSession = queueConnection.createQueueSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            }
            return queueSession;
        } catch (JMSException e) {
            log.error("Error creating queue session", e);
            throw new TransportException("Error creating queue session");
        }
    }

    private QueueConnection getQueueConnection() {

        try {
            if (queueConnection == null) {
                queueConnection = queueConnectionFactory.createQueueConnection();
            }
            return queueConnection;
        } catch (JMSException e) {
            throw new TransportException("Error creating queue connection", e);
        }
    }

    @Override
    public byte[] getBytes() throws TransportException {

        final long timeOut = 60000;
        QueueReceiver receiver;
        Message replyMessage;

        try {
            receiver = this.getQueueSession()
                    .createReceiver(responseQueue, "JMSCorrelationID = '" + this.correlationId + "'");

            getQueueConnection().start();
            replyMessage = receiver.receive(timeOut);

            // If receive return null then message has timed out
            if (replyMessage == null) {
                log.error("Messaging: Reply timed out for {}", this.transactionName);
                throw new TransportTimeoutException("Messaging: Reply timed out for " + this.transactionName);
            }

            // return the unpacked payload of replyMessage
            byte[] replyBytes;
            TransportHelper transportHelper = new TransportHelper();

            replyBytes = transportHelper.getBytesFromMessage((BytesMessage) replyMessage);
            return replyBytes;

        } catch (JMSException e) {
            log.error("Error creating queue receiver", e);
            throw new TransportTimeoutException("Error creating queue receiver", e);
        }
    }

    @Override
    public String sendBytes(byte[] data) {
        correlationId = this.createAndSendMessage(data);
        return correlationId;
    }

    private String createAndSendMessage(byte[] data) {

        QueueSender sender = null;
        Message message;

        try {
            QueueSession queueSession = getQueueSession();

            if ("text".equalsIgnoreCase(this.messageType)) {
                message = queueSession.createMessage();
                ((TextMessage)message).setText(new String(data));
            } else {
                message = queueSession.createBytesMessage();
                ((BytesMessage) message).writeBytes(data);
            }

            message.setJMSReplyTo(this.responseQueue);
            message.setStringProperty("transactionName", this.transactionName);

            if (StringUtils.hasText(routingKey)) {
                message.setStringProperty("routingKey", this.routingKey);
            }

            sender = queueSession.createSender(this.requestQueue);

            // Send the messaage and get CorrelationId
            sender.send(message);
            log.info("Message sent. routingKey: {}, transactionName:{}",
                    this.routingKey, this.transactionName);

            // Return the JMS message ID of the request, so it can be used as the
            //correlationID of the reply.
            return message.getJMSMessageID();

        } catch (JMSException e) {
            log.error("Error while sending message", e);
            throw new TransportException("Messaging: JMS Exception occurred", e);
        } finally {
            this.closeAll(sender);
        }
    }
}
