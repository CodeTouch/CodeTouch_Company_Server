package com.tagmaster.codetouch.controller;

import com.tagmaster.codetouch.dto.APIPhoneDTO;
import com.tagmaster.codetouch.dto.APISignupDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepository;
import com.tagmaster.codetouch.service.identity.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/패스")
public class PASSCtrl {

    private final AuthService authService;
    private final CompanyUserRepository companyUserRepository;

    public PASSCtrl(AuthService authService, CompanyUserRepository companyUserRepository) {
        this.authService = authService;
        this.companyUserRepository = companyUserRepository;
    }

    @GetMapping("/인증/{imp_uid}")
    public ResponseEntity<Map<String, Object>> getCertifications(@PathVariable String imp_uid) {
        try {
            Map<String, Object> response = new HashMap<>();
            String accessToken = authService.TokenService();
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

    @GetMapping("/회원/아이디찾기/{imp_uid}")
    public ResponseEntity<Map<String, Object>> EmailFind(@PathVariable String imp_uid) {
        try {
            Map<String, Object> response = new HashMap<>();
            String accessToken = authService.TokenService();
            APIPhoneDTO dto = authService.AuthReqService(APIPhoneDTO.class, imp_uid, accessToken);
            CompanyUser user = companyUserRepository.findByPhone(dto.getPhone());
            List<CompanyUser> test = companyUserRepository.findAll();
            if (user.getEmail() == null) {
                return ResponseEntity.badRequest().body(response);
            }
            response.put("success", "이메일로 받기 성공");
            response.put("email", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("fail", "이메일로 찾기 실패띠");
            return ResponseEntity.badRequest().body(response);
        }
    }
}

