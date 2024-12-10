package com.tagmaster.codetouch.entity.company;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pmId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private CompanyUser user;
    private String cardType;
    private String pw;
    private LocalDateTime cardExpiryDate;
    private LocalDateTime birth;
    private String password;
    private LocalDateTime createdAt;
}
