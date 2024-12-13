package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.company.SignupDTO;

import com.tagmaster.codetouch.service.company.SignupSvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/회사")
public class SignupCtrl {

    private final SignupSvc signupSvc;

    public SignupCtrl(SignupSvc signupSvc) {
        this.signupSvc = signupSvc;
    }

    @PostMapping("/회원/회원가입")
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
        try {
            // 서비스 호출
            signupSvc.SignupProcess(signupDTO);

            // 성공 시 응답
            return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
        } catch (IllegalArgumentException e) {

            // 중복된 사용자 이름 등 특정 예외 처리
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 사용자 이름입니다.");
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다.");
        }
    }
    @GetMapping("/회원/회원가입/중복확인/{email}")
    public ResponseEntity<String> signupDup(@PathVariable String email) {
        try{
            if(signupSvc.isEmailAvailable(email)){
                return ResponseEntity.ok("사용 가능한 이메일입니다.");
            }
            return ResponseEntity.badRequest().body("이메일이 중복됩니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이메일 중복 확인 중 에러");
        }
    }
}