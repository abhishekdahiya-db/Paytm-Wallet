package com.wallet.transaction.entity;

import com.wallet.transaction.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @Column(nullable = false,unique = true)
    private String transactionId;

    @Column(nullable = false)
    private long fromUserId;

    @Column(nullable = false)
    private long toUserId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column
    private String reason;

}
