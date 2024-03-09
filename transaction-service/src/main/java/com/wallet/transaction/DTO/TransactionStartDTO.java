package com.wallet.transaction.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionStartDTO {
    @NotNull
    private long fromUserId;
    @NotNull
    private long toUserId;
    @NotNull
    private Double transactionAmount;
}
