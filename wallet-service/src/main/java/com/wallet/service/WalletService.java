package com.wallet.service;

import com.wallet.kafka.DTO.UserCreatedKafkaObject;

public interface WalletService {
    public void createWalletForRegisteredUser(UserCreatedKafkaObject userObject);
}
