package com.wallet.transaction.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.kafka.DTO.TransactionCompletedKafkaObject;
import com.wallet.transaction.service.TransactionService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class KafkaConsumerConfig {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TransactionService service;
    @KafkaListener(topics = "TNX-COMPLETED",groupId = "transactionApp")
    private void markTransactionCompletion(ConsumerRecord payload) throws JsonProcessingException {
        String json = payload.value().toString();
        TransactionCompletedKafkaObject completedKafkaObject = mapper.readValue(json, TransactionCompletedKafkaObject.class);
        service.markTransactionCompleted(completedKafkaObject);
    }
}
