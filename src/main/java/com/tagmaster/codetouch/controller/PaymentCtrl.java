package com.tagmaster.codetouch.controller;

import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.service.PaymentSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/회사")
public class PaymentCtrl {
    private final PaymentSvc paymentSvc;

    @Autowired
    public PaymentCtrl(PaymentSvc paymentSvc) {
        this.paymentSvc = paymentSvc;
    }

    @PostMapping("/회원/결제내역저장")
    public ResponseEntity<String> signup(@RequestBody PaymentDTO paymentDTO) {
        String message = "";
        message += paymentSvc.Save(paymentDTO);
        message += paymentSvc.UpgradeSite(paymentDTO);

        return ResponseEntity.ok(message);
    }
}
