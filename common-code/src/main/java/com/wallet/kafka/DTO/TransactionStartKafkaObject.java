package com.wallet.kafka.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStartKafkaObject {
    private String transactionId;
    private long fromUserId;
    private long toUserId;
    private double amount;

}
