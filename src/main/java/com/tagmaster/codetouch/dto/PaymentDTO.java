package com.tagmaster.codetouch.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDTO {
    private String email;
    private int siteId;
    private LocalDate expiry;
    private String merchantId;
}
