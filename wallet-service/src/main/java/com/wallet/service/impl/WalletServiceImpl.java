package com.wallet.service.impl;

import com.wallet.DAO.IWalletRepo;
import com.wallet.entity.UserWallet;
import com.wallet.kafka.DTO.TransactionCompletedKafkaObject;
import com.wallet.kafka.DTO.TransactionStartKafkaObject;
import com.wallet.kafka.DTO.UserCreatedKafkaObject;
import com.wallet.kafka.DTO.WalletUpdatedKafkaObject;
import com.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private static final String WALLET_UPDATED = "wallet_updated";
    private static final String TRANSACTION_COMPLETED = "TNX-COMPLETED";
    @Autowired
    private IWalletRepo repository;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public void createWalletForRegisteredUser(UserCreatedKafkaObject userObject) {
        UserWallet wallet = UserWallet.builder().userId(userObject.getId()).amount(100.0)
                .emailId(userObject.getEmailId()).userName(userObject.getName()).build();
        repository.save(wallet);
        // send msg to kafka of notify to send message for balance 100 given.
        WalletUpdatedKafkaObject kafkaObject= new WalletUpdatedKafkaObject(100.0,100.0,wallet.getUserId(), wallet.getEmailId(), wallet.getUserName(), "CREDIT");
        kafkaTemplate.send(WALLET_UPDATED, String.valueOf(wallet.getUserId()),kafkaObject);
    }

    @Override
    @Transactional
    public void startTransaction(TransactionStartKafkaObject transactionStartKafkaObject) {
        UserWallet walletA = repository.findByUserId(transactionStartKafkaObject.getFromUserId());
        UserWallet walletB = repository.findByUserId(transactionStartKafkaObject.getToUserId());
        TransactionCompletedKafkaObject completedKafkaObject = new TransactionCompletedKafkaObject();
        completedKafkaObject.setTransactionId(transactionStartKafkaObject.getTransactionId());
        if(walletA.getAmount() >= transactionStartKafkaObject.getAmount()){
            walletA.setAmount(walletA.getAmount() - transactionStartKafkaObject.getAmount());
            WalletUpdatedKafkaObject kafkaObject1 = new WalletUpdatedKafkaObject(walletA.getAmount(), transactionStartKafkaObject.getAmount(), walletA.getUserId(), walletA.getEmailId(), walletA.getUserName(), "DEBIT");
            walletB.setAmount(walletB.getAmount() + transactionStartKafkaObject.getAmount());
            WalletUpdatedKafkaObject kafkaObject2 = new WalletUpdatedKafkaObject(walletB.getAmount(), transactionStartKafkaObject.getAmount(), walletB.getUserId(), walletB.getEmailId(), walletB.getUserName(), "CREDIT");
            completedKafkaObject.setStatus("completed");
            kafkaTemplate.send(WALLET_UPDATED, String.valueOf(walletA.getWalletId()),kafkaObject1);
            kafkaTemplate.send(WALLET_UPDATED, String.valueOf(walletB.getWalletId()),kafkaObject2);
        }else{
            completedKafkaObject.setStatus("failed");
            completedKafkaObject.setReason("Insufficient balance.");
        }
        kafkaTemplate.send(TRANSACTION_COMPLETED,completedKafkaObject.getTransactionId(),completedKafkaObject);
    }
}
