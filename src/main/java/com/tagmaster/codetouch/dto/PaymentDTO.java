package com.tagmaster.codetouch.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDTO {
    private String userEmail;
    private int siteId;
    private LocalDate expiryDate;
}
