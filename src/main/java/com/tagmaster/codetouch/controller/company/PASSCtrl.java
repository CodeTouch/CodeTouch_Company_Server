package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.APIPhoneDTO;
import com.tagmaster.codetouch.dto.APISignupDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.service.identity.AuthSvc;
import com.tagmaster.codetouch.util.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/회사")
public class PASSCtrl {

    private final AuthSvc authService;

    public PASSCtrl(AuthSvc authService) {
        this.authService = authService;
    }

    @GetMapping("/패스/인증/{imp_uid}")
    public ResponseEntity<Map<String, Object>> getCertifications(@PathVariable String imp_uid) {
        try {
            Map<String, Object> response = new HashMap<>();
            String accessToken = TokenUtil.TokenUtil();
            APISignupDTO dto = authService.AuthReqService(APISignupDTO.class, imp_uid, accessToken);
            response.put("success", "api로 개인정보 받기 성공");
            response.put("data", dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("fail", "인증에 실패띠");
            return ResponseEntity.badRequest().body(response);
        }
    }

}

