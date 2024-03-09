package com.wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long walletId;

    @Column(nullable = false,unique = true)
    private long userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false,unique = true)
    private String emailId;

    @Column(nullable = false)
    private String userName;


}
