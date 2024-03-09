package com.wallet.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.service.WalletService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private WalletService walletService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "USER",groupId = "walletApp")
    public void consumeKafkaUserCreatedObject(ConsumerRecord payload) throws JsonProcessingException {
        String json = payload.value().toString();
        UserCreatedKafkaObject createdUser = objectMapper.readValue(json,UserCreatedKafkaObject.class);
        walletService.createWalletForRegisteredUser(createdUser);
    }
}
