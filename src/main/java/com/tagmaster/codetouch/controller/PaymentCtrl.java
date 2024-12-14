
package com.tagmaster.codetouch.controller;

import com.tagmaster.codetouch.dto.PayReadDTO;
import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.service.PaymentSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/회사")
public class PaymentCtrl {
    private final PaymentSvc paymentSvc;


    @Autowired
    public PaymentCtrl(PaymentSvc paymentSvc) {
        this.paymentSvc = paymentSvc;
    }

    @PostMapping("/회원/결제내역저장")
    public ResponseEntity<String> paymentSave(@RequestBody PaymentDTO paymentDTO) {
        String message = "";
        message += paymentSvc.Save(paymentDTO);
        message += paymentSvc.UpgradeSite(paymentDTO);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/회원/결제내역조회/{email}")
    public ResponseEntity<String> paymentRead(@PathVariable String email) {
        if (paymentSvc.Read(email) == null) {
            return ResponseEntity.badRequest().body("에러");
        }
        List<PayReadDTO> result = paymentSvc.Read(email);
        return ResponseEntity.ok(result.toString());
    }
}
//merchantId => dto로만 받아와도 된다