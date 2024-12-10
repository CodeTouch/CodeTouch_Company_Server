//package com.tagmaster.codetouch.controller;
//
//import com.tagmaster.codetouch.dto.APIPhoneDTO;
//import com.tagmaster.codetouch.dto.APISignupDTO;
//import com.tagmaster.codetouch.entity.company.CompanyUser;
//import com.tagmaster.codetouch.repository.company.CompanyUserRepository;
//import com.tagmaster.codetouch.service.identity.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//@RestController
//@RequestMapping("/회사")
//public class PASSPhoneCtrl {
//
//   private final AuthService authService;
//   private final CompanyUserRepository companyUserRepository;
//
//   @Autowired
//   public PASSPhoneCtrl(AuthService authService, CompanyUserRepository companyUserRepository) {
//       this.authService = authService;
//       this.companyUserRepository = companyUserRepository;
//   }
//   @GetMapping("/회원/아이디찾/{imp_uid}")
//   public ResponseEntity<Map<String,Object>> getCertifications(@PathVariable String imp_uid) throws Exception {
//       Map<String,Object> response = new HashMap<>();
//       String accessToken = authService.TokenService();
//       APIPhoneDTO dto = authService.AuthReqService(APIPhoneDTO.class, imp_uid, accessToken);
//       CompanyUser user = companyUserRepository.findByPhone(dto.getPhone());
//       if (user.getEmail() == null){
//           return ResponseEntity.badRequest().body(response);
//       }
//       response.put("message", "이메일로 받기 성공");
//       response.put("email", user.getEmail());
//       return ResponseEntity.ok(response);
//   }
//}
//
