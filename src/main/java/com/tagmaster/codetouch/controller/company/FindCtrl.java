package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.PwFindDTO;
import com.tagmaster.codetouch.service.UserSvc;
import com.tagmaster.codetouch.service.identity.AuthSvc;
import com.tagmaster.codetouch.util.TokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/회사")
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class FindCtrl {
    private final UserSvc userSvc;
    private final AuthSvc authSvc;

    public FindCtrl(UserSvc userSvc, AuthSvc authSvc) {
        this.userSvc = userSvc;
        this.authSvc = authSvc;
    }

    @GetMapping("/회원/아이디찾기/{imp_uid}")
    public ResponseEntity<Map<String, Object>> EmailFind(@PathVariable String imp_uid) {
        Map<String, Object> response = new HashMap<>();
        String result = userSvc.emailFind(imp_uid);
        if (result == null) {
            return ResponseEntity.badRequest().body(response);
        }
        if (result.equals("FAIL")) {
            response.put("FAIL", "이메일 찾기 실패띠");
            response.put("email", result);
            return ResponseEntity.badRequest().body(response);
        }
        if (result.equals("ERROR")) {
            response.put("ERROR", "이메일 찾는 도중 에러");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("SUCCESS", "이메일로 받기 성공");
        response.put("email", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/회원/비번찾기/{email}/{imp_uid}")
    public ResponseEntity<String> pwAuth(@PathVariable String email, @PathVariable String imp_uid) {
        String accessToken = TokenUtil.TokenUtil();
        PwFindDTO pwFindDTO = authSvc.AuthReqService(PwFindDTO.class, imp_uid, accessToken);
        pwFindDTO.setEmail(email);
        String pw = userSvc.findPw(pwFindDTO);
        if (pw.equals("SUCCESS")) {
            return ResponseEntity.ok("입력한 정보가 일치합니다.");
        } else if (pw.equals("FAIL")) {
            return ResponseEntity.badRequest().body("비밀번호를 찾지 못했습니다. 다시 시도해주세요.");
        }
        return ResponseEntity.badRequest().body("본인 인증에 에러가 났습니다. 다시 시도해주세요.");
    }

    @PostMapping("/회원/비번재설정")
    public ResponseEntity<String> pwFind(@RequestBody PwChangeDTO pwChangeDTO) {
        String pwChange = userSvc.changePw(pwChangeDTO);
        if (pwChange.equals("SUCCESS")) {
            return ResponseEntity.ok("새 비밀번호 재설정이 완료되었습니다.");
        } else if (pwChange.equals("FAIL")) {
            return ResponseEntity.badRequest().body("비밀번호가 변경되지 않았습니다. 다시 시도해주세요.");
        }
        return ResponseEntity.badRequest().body("본인 인증에 에러가 났습니다. 다시 시도해주세요.");
    }
}
