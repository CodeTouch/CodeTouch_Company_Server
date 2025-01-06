package com.tagmaster.codetouch.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class APIPaymentDTO {
    private String payMethod;
    private Integer amount;
}
