package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.company.ChangeInfoDTO;
import com.tagmaster.codetouch.service.ChangeUserInfoSvc;
import com.tagmaster.codetouch.service.UserSvc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/회사")
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class UserCtrl {
    private final UserSvc userSvc;
    private final ChangeUserInfoSvc changeUserInfoSvc;
    public UserCtrl(UserSvc userSvc, ChangeUserInfoSvc changeUserInfoSvc) {
        this.userSvc = userSvc;
        this.changeUserInfoSvc = changeUserInfoSvc;
    }
    @PostMapping("/회원/개인정보수정")
    public ResponseEntity<String> infoChange(@RequestParam String email, @RequestParam String nickname, @RequestParam String name, @RequestParam String phone, @RequestParam String updateImageName, @RequestParam("updateImage") MultipartFile updateImage) {
        try{
            ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
            changeInfoDTO.setEmail(email);
            changeInfoDTO.setNickname(nickname);
            changeInfoDTO.setName(name);
            changeInfoDTO.setPhone(phone);
            changeInfoDTO.setUpdateImageName(updateImageName);
            changeInfoDTO.setUpdateImage(updateImage);

        if (changeUserInfoSvc.changeUserInfo(changeInfoDTO)) {
            return ResponseEntity.ok("수정이 완료되었습니다.");
        }
        return ResponseEntity.badRequest().body("수정에 실패하였습니다. 다시 시도해주세요.");
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
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러");
        }
    }
}
