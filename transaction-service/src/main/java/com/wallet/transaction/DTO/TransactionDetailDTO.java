package com.wallet.transaction.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDetailDTO {
    private String status;
    private String reason;
}
