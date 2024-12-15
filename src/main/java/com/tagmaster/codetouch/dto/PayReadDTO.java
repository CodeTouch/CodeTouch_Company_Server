package com.tagmaster.codetouch.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PayReadDTO {
    private String siteName;
    private String merchantId;
    private LocalDate expiry;
    private String payMethod;
    private Integer amount;
    private Integer payState;
    private String createAt;
}
