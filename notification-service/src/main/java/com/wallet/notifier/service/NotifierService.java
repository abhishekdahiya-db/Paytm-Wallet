package com.wallet.notifier.service;

import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.kafka.DTO.WalletUpdatedKafkaObject;

public interface NotifierService {
    public void sendWelcomeEmail(UserCreatedKafkaObject object);
    public void balanceUpdateEmail(WalletUpdatedKafkaObject object);
}
