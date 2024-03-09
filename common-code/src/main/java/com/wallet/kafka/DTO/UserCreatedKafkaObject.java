package com.wallet.kafka.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedKafkaObject {
    private long id;
    private String name;
    private String emailId;
}
