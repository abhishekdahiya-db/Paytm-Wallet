package com.wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Entity
@Data
@Builder
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long walletId;

    @Column(nullable = false,unique = true)
    private long userId;

    @Column(nullable = false)
    private Double amount;


}
