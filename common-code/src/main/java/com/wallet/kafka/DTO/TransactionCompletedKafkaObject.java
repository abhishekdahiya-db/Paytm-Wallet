package com.wallet.kafka.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCompletedKafkaObject {
    private String transactionId;
    private String status;
    private String reason;
}
