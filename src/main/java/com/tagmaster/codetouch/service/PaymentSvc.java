package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.entity.company.Payment;

public interface PaymentSvc {
    String Save(PaymentDTO payment);

    String UpgradeSite(PaymentDTO payment);

    String Read(PaymentDTO payment);
}
