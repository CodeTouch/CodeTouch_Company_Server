package com.tagmaster.codetouch.entity.company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private CompanyUser user;
    private int siteId;

    @ManyToOne
    @JoinColumn(name = "pmId", nullable = false)
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
}
