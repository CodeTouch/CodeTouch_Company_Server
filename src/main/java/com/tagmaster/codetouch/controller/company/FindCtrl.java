package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.PwFindDTO;
import com.tagmaster.codetouch.service.UserSvc;
import com.tagmaster.codetouch.service.identity.AuthSvc;
import com.tagmaster.codetouch.util.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/회사")
public class FindCtrl {
    private final UserSvc findSvc;
    private final AuthSvc authSvc;

    public FindCtrl(UserSvc findSvc, AuthSvc authSvc) {
        this.findSvc = findSvc;
        this.authSvc = authSvc;
    }

    @GetMapping("/회원/비번찾기/{email}/{imp_uid}")
    public ResponseEntity<String> pwAuth(@PathVariable String email, @PathVariable String imp_uid){
        String accessToken = TokenUtil.TokenUtil();
        PwFindDTO pwFindDTO = authSvc.AuthReqService(PwFindDTO.class, imp_uid, accessToken);
        pwFindDTO.setEmail(email);
        if (findSvc.findPw(pwFindDTO).equals("SUCCESS")) {
            return ResponseEntity.ok("입력한 정보가 일치합니다.");
        } else if (findSvc.findPw(pwFindDTO).equals("FAIL")){
            return ResponseEntity.badRequest().body("비밀번호를 찾지 못했습니다. 다시 시도해주세요.");
        }
        return ResponseEntity.badRequest().body("본인 인증에 에러가 났습니다. 다시 시도해주세요.");
    }

    @PostMapping("/회원/비번재설정")
    public ResponseEntity<String> pwFind(@RequestBody PwChangeDTO pwChangeDTO) {
        String pwChange = findSvc.changePw(pwChangeDTO);
        if (pwChange.equals("SUCCESS")) {
            return ResponseEntity.ok("새 비밀번호 재설정이 완료되었습니다.");
        } else if (pwChange.equals("FAIL")) {
            return ResponseEntity.badRequest().body("비밀번호가 변경되지 않았습니다. 다시 시도해주세요.");
        }
        return ResponseEntity.badRequest().body("본인 인증에 에러가 났습니다. 다시 시도해주세요.");
    }
}
