package com.example.api.payment.mqservice.mqconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("api.mq.jms.connection.factory")
public class MqJmsConnectionFactory {

    private String cCSID;
    private String messageRetention;
    private String messageBatchSize;
    private String pollingInterval;
    private String queueManager;
    private String syncPointAllGets;
    private String useConnectionPooling;
    private String transPortType;
    private String channel;
    private String hostName;
    private String port;
    private String connectionNameList;
    private String temporaryModel;
    private String sSlCipherSuite;
}
