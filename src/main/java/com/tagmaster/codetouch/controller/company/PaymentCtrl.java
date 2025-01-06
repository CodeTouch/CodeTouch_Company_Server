
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
            if (paymentSvc.Save(paymentDTO).equals("성공")){
                if (paymentSvc.UpgradeSite(paymentDTO).equals("성공")){
                    return ResponseEntity.ok("결제 정보가 성공적으로 저장되었습니다.");
                }
            }
            return ResponseEntity.badRequest().body("결제 정보를 저장하지 못하였습니다. 다시 시도해주세요.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("결제정보를 저장하던 도중 에러가 발생했습니다. 다시 시도해주세요.");
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