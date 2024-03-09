package com.wallet.transaction.service.impl;

import com.wallet.kafka.DTO.TransactionCompletedKafkaObject;
import com.wallet.kafka.DTO.TransactionStartKafkaObject;
import com.wallet.transaction.DTO.TransactionStartDTO;
import com.wallet.transaction.dao.ITransactionRepo;
import com.wallet.transaction.entity.Transaction;
import com.wallet.transaction.enums.TransactionStatus;
import com.wallet.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final String TRANSACTION_START = "TNX-INIT";
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    private ITransactionRepo repository;
    @Override
    public String initTransaction(TransactionStartDTO transactionStartDTO) {
        Transaction newTransaction = mapDtoToEntity(transactionStartDTO);
        repository.save(newTransaction);
        // push new transaction object to init transaction topic of kafka
        TransactionStartKafkaObject kafkaObject = new TransactionStartKafkaObject(newTransaction.getTransactionId(), newTransaction.getFromUserId(),
                newTransaction.getToUserId(), newTransaction.getAmount());
        kafkaTemplate.send(TRANSACTION_START,newTransaction.getTransactionId(),kafkaObject);
        return newTransaction.getTransactionId();
    }

    @Override
    public void markTransactionCompleted(TransactionCompletedKafkaObject transactionCompletedKafkaObject) {
        Transaction transaction = repository.findByTransactionId(transactionCompletedKafkaObject.getTransactionId());
        transaction.setStatus(transactionCompletedKafkaObject.getStatus().equals("completed") ? TransactionStatus.COMPLETED : TransactionStatus.FAILED);
        transaction.setReason(transactionCompletedKafkaObject.getReason());
        repository.save(transaction);
    }

    private Transaction mapDtoToEntity(TransactionStartDTO transactionStartDTO){
        return Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .fromUserId(transactionStartDTO.getFromUserId())
                .toUserId(transactionStartDTO.getToUserId())
                .amount(transactionStartDTO.getTransactionAmount())
                .status(TransactionStatus.PENDING)
                .build();
    }
}
