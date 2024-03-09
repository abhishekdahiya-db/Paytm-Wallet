package com.wallet.user.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    private String id;
    @NotNull
    private String emailId;
    @NotNull
    private String name;
    @NotNull
    private String kycNumber;

}
