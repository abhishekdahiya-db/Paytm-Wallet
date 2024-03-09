package com.wallet.transaction.service;

import com.wallet.kafka.DTO.TransactionCompletedKafkaObject;
import com.wallet.transaction.DTO.TransactionStartDTO;

public interface TransactionService {
    public String initTransaction(TransactionStartDTO transactionStartDTO);
    public void markTransactionCompleted(TransactionCompletedKafkaObject transactionCompletedKafkaObject);
}
