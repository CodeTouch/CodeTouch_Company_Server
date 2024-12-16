package com.tagmaster.codetouch.service.pay;

import com.tagmaster.codetouch.dto.PayReadDTO;
import com.tagmaster.codetouch.dto.PaymentDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public interface PaymentSvc {
    String Save(PaymentDTO payment);

    String UpgradeSite(PaymentDTO payment);

    List<PayReadDTO> Read(String email, boolean check);
}
