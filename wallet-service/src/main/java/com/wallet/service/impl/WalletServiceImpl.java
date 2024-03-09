package com.wallet.service.impl;

import com.wallet.DAO.IWalletRepo;
import com.wallet.entity.UserWallet;
import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.kafka.DTO.WalletUpdatedKafkaObject;
import com.wallet.service.WalletService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private static final String WALLET_UPDATED = "wallet_updated";
    @Autowired
    private IWalletRepo repository;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void createWalletForRegisteredUser(UserCreatedKafkaObject userObject) {
        UserWallet wallet = UserWallet.builder().userId(userObject.getId()).amount(100.0).build();
        repository.save(wallet);
        // send msg to kafka of notify to send message for balance 100 given.
        WalletUpdatedKafkaObject kafkaObject= new WalletUpdatedKafkaObject(100.0,100.0,wallet.getUserId(), userObject.getEmailId(), userObject.getName(),"CREDIT");
        kafkaTemplate.send(WALLET_UPDATED, String.valueOf(wallet.getUserId()),kafkaObject);
    }
}
