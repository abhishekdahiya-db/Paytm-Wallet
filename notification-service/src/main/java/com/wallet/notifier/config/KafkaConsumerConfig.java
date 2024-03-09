package com.wallet.notifier.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.kafka.DTO.WalletUpdatedKafkaObject;
import com.wallet.notifier.service.NotifierService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumerConfig {
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    @Autowired
    private NotifierService notifyService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "USER",groupId = "welcomeEmail")
    public void sendWelcomeEmail(ConsumerRecord payload) throws JsonProcessingException {
        // send welcome message to created user
        String json = payload.value().toString();
        UserCreatedKafkaObject createdUserKafkaObject = objectMapper.readValue(json, UserCreatedKafkaObject.class);
        logger.info("welcome email kafka object received at notifier {}",createdUserKafkaObject);
        notifyService.sendWelcomeEmail(createdUserKafkaObject);
    }

    @KafkaListener(topics = "wallet_updated",groupId = "balanceUpdateEmail")
    public void sendBalanceUpdateEmail(ConsumerRecord payload) throws JsonProcessingException {
        String json = payload.value().toString();
        WalletUpdatedKafkaObject walletUpdatedKafkaObject = objectMapper.readValue(json,WalletUpdatedKafkaObject.class);
        logger.info("update wallet balance email kafka object received at notifier {}",walletUpdatedKafkaObject);
        notifyService.balanceUpdateEmail(walletUpdatedKafkaObject);
    }
}
