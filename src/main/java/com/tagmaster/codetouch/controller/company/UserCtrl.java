package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.company.ChangeInfoDTO;
import com.tagmaster.codetouch.service.UserSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/회사")
public class UserCtrl {
    private final UserSvc userSvc;
    public UserCtrl(UserSvc userSvc) {
        this.userSvc = userSvc;
    }
    @PostMapping("/회원/개인정보수정")
    public ResponseEntity<String> infoChange(ChangeInfoDTO changeInfoDTO) {
        try{
        if (userSvc.changeUserInfo(changeInfoDTO)){
            return ResponseEntity.ok("닉네임 수정이 완료되었습니다.");
        }
        return ResponseEntity.badRequest().body("닉네임 수정에 실패하였습니다. 다시 시도해주세요.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러");
        }
    }
    @PostMapping("/회원/비밀번호확인")
    public ResponseEntity<String> pwCorrectCheck(@RequestBody PwChangeDTO pwChangeDTO) {
        try{
            if (userSvc.isPwCorrect(pwChangeDTO)){
                return ResponseEntity.ok("비밀번호가 일치합니다.");
            }
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러");
        }
    }
}
