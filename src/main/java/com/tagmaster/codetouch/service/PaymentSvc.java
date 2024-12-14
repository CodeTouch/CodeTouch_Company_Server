package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.PayReadDTO;
import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.entity.company.Payment;

import java.util.List;

public interface PaymentSvc {
    String Save(PaymentDTO payment);

    String UpgradeSite(PaymentDTO payment);

    List<PayReadDTO> Read(String email);
}
