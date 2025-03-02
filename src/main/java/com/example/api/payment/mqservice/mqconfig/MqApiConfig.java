package com.example.api.payment.mqservice.mqconfig;

import com.example.api.payment.mqservice.transport.TransportHandler;
import com.example.api.payment.mqservice.transport.impl.TransportHandlerImpl;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

@Getter
@Setter
@Configuration
public class MqApiConfig {

    final MqJmsConnectionFactory mqJmsConnectionFactory;
    final MqJmsRequestQueue mqJmsRequestQueue;
    final MqJmsReplyQueue mqJmsReplyQueue;
    final SSLContextBuilder sslContextBuilder;

    public MqApiConfig(MqJmsConnectionFactory mqJmsConnectionFactory, MqJmsRequestQueue mqJmsRequestQueue, MqJmsReplyQueue mqJmsReplyQueue, SSLContextBuilder sslContextBuilder) {
        this.mqJmsConnectionFactory = mqJmsConnectionFactory;
        this.mqJmsRequestQueue = mqJmsRequestQueue;
        this.mqJmsReplyQueue = mqJmsReplyQueue;
        this.sslContextBuilder = sslContextBuilder;
    }

    @Bean(name ="mqConnectionFactory")
    public QueueConnectionFactory mqConnectionFactory() throws JMSException {

        final MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
        connectionFactory.setChannel(mqJmsConnectionFactory.getChannel());
        connectionFactory.setHostName(mqJmsConnectionFactory.getHostName());
        connectionFactory.setPort(Integer.parseInt(mqJmsConnectionFactory.getPort()));
        connectionFactory.setSSLSocketFactory(sslContextBuilder.getSslSocketFactory());

        connectionFactory.setMessageRetention(Integer.parseInt(mqJmsConnectionFactory.getMessageRetention()));
        connectionFactory.setMsgBatchSize(Integer.parseInt(mqJmsConnectionFactory.getMessageBatchSize()));
        connectionFactory.setPollingInterval(Integer.parseInt(mqJmsConnectionFactory.getPollingInterval()));
        connectionFactory.setSyncpointAllGets(Boolean.parseBoolean(mqJmsConnectionFactory.getSyncPointAllGets()));
        connectionFactory.setTransportType(Integer.parseInt(mqJmsConnectionFactory.getTransPortType()));
        connectionFactory.setCCSID(Integer.parseInt(mqJmsConnectionFactory.getCCSID()));
        connectionFactory.setTemporaryModel(mqJmsConnectionFactory.getTemporaryModel());
        connectionFactory.setSSLCipherSuite(mqJmsConnectionFactory.getSSlCipherSuite());

        return connectionFactory;
    }

    @Bean(name="mqRequestQueue")
    public MQQueue getMqRequestQueue() throws JMSException {

        final MQQueue mqRequestQueue = new MQQueue();
        mqRequestQueue.setBaseQueueName(mqJmsRequestQueue.getBaseQueueName());
        mqRequestQueue.setEncoding(mqRequestQueue.getEncoding());
        mqRequestQueue.setExpiry(mqRequestQueue.getExpiry());
        mqRequestQueue.setPersistence(mqRequestQueue.getPersistence());
        mqRequestQueue.setPriority(mqRequestQueue.getPriority());
        mqRequestQueue.setTargetClient(mqRequestQueue.getTargetClient());

        return mqRequestQueue;
    }

    @Bean(name="mqReplyQueue")
    public MQQueue getMqReplyQueue() throws JMSException {
        final MQQueue mqReplyQueue = new MQQueue();
        mqReplyQueue.setBaseQueueName(mqJmsReplyQueue.getBaseQueueName());
        mqReplyQueue.setEncoding(mqReplyQueue.getEncoding());
        mqReplyQueue.setExpiry(mqReplyQueue.getExpiry());
        mqReplyQueue.setPersistence(mqReplyQueue.getPersistence());
        mqReplyQueue.setPriority(mqReplyQueue.getPriority());
        mqReplyQueue.setTargetClient(mqReplyQueue.getTargetClient());

        return mqReplyQueue;
    }

    @Bean(name="mqCachingConnectionFactory")
    public CachingConnectionFactory getMqCachingConnectionFactory(
            @Autowired(required = false) @Qualifier("mqConnectionFactory")
            QueueConnectionFactory targetConnectionFactory,
            @Value("${api.mq.jms.cache.size:1}") int sessionCacheSize,
            @Value("${api.mq.mq.jms.cache.reconnectOnException:true}") boolean reconnectOnException,
            @Value("${api.mq.jms.cache.producers:true}") boolean cacheProducers,
            @Value("${api.mq.jms.cache.consumers:false}") boolean cacheConsumers
    ) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(targetConnectionFactory);
        connectionFactory.setSessionCacheSize(sessionCacheSize);
        connectionFactory.setReconnectOnException(reconnectOnException);
        connectionFactory.setCacheProducers(cacheProducers);
        connectionFactory.setCacheConsumers(cacheConsumers);
        return connectionFactory;
    }

    @Bean(name="mqTransportHandler")
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public TransportHandler mqTransportHandler(
            @Autowired(required = false) @Qualifier("mqCachingConnectionFactory")
            QueueConnectionFactory queueConnectionFactory,
            @Autowired(required = false) @Qualifier("mqRequestQueue") Queue requestQueue,
            @Autowired(required = false) @Qualifier("mqReplyQueue") Queue replyQueue
    ) {

    return TransportHandlerImpl.builder()
            .queueConnectionFactory(queueConnectionFactory)
            .requestQueue(requestQueue)
            .responseQueue(replyQueue)
            .build();
    }

    @PostConstruct
    public void setSystemProperties() {
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
    }
}
