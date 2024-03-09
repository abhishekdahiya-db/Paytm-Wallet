package com.wallet.kafka.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletUpdatedKafkaObject {
    private double amount;
    private double transactionAmount;
    private long userId;
    private String emailId;
    private String name;
    private String action;
}
