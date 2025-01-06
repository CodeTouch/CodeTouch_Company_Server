package com.tagmaster.codetouch.dto;

import com.tagmaster.codetouch.entity.customer.CustomerUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SiteInfoDTO {
    private CustomerUser userId;
    private String siteName;
    private Integer payState;
    private LocalDate expiry;
}
