package com.wallet.service;

import com.wallet.kafka.DTO.TransactionStartKafkaObject;
import com.wallet.kafka.DTO.UserCreatedKafkaObject;

public interface WalletService {
    public void createWalletForRegisteredUser(UserCreatedKafkaObject userObject);
    public void startTransaction(TransactionStartKafkaObject transactionStartKafkaObject);
}
