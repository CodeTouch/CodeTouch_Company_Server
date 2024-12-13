package com.tagmaster.codetouch.dto;

import com.tagmaster.codetouch.entity.company.CompanyUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayReadDTO {
    private CompanyUser user;
    private String siteName;
    private String merchantId;

}
