package com.tagmaster.codetouch.dto;

import com.tagmaster.codetouch.entity.company.CompanyUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PayReadDTO {
    private CompanyUser user;
    private String siteName;
    private String merchantId;
    private LocalDateTime createAt;
}
