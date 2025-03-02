package com.example.api.payment.mqservice.mqconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("api.mq.jms.reply.queue")
public class MqJmsReplyQueue {

    private String baseQueueName;
    private String cCSID;
    private String encoding;
    private String targetClient;
}
