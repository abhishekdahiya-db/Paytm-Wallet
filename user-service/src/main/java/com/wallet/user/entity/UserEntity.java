package com.wallet.user.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class UserEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(nullable = false, unique = true)
    private String emailId;
    @Column(nullable = false, unique = true)
    private String kycNumber;
    @Column(nullable = false)
    private String name;




}
