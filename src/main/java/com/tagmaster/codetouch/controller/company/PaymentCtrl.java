
package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.PayReadDTO;
import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.service.pay.PaymentSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/회사")
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class PaymentCtrl {
    private final PaymentSvc paymentSvc;


    @Autowired
    public PaymentCtrl(PaymentSvc paymentSvc) {
        this.paymentSvc = paymentSvc;
    }

    @PostMapping("/회원/결제내역저장")
    public ResponseEntity<String> paymentSave(@RequestBody PaymentDTO paymentDTO) {
        try {
            String message = "";
            message += paymentSvc.Save(paymentDTO);
            message += paymentSvc.UpgradeSite(paymentDTO);

            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }
    }

    @GetMapping("/회원/결제내역조회/{email}/{check}")
    public ResponseEntity<List<PayReadDTO>> paymentRead(@PathVariable String email, @PathVariable boolean check) {
        List<PayReadDTO> result = paymentSvc.Read(email, check);
        if (result == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(result);
    }
}