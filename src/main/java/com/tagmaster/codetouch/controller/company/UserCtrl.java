package com.tagmaster.codetouch.controller.company;

import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.company.ChangeInfoDTO;
import com.tagmaster.codetouch.service.ChangeUserInfoSvc;
import com.tagmaster.codetouch.service.UserSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/회사")
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
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러");
        }
    }
//    @PostMapping("/이미지저장")
//    public ResponseEntity<String> imageSave(@RequestParam MultipartFile file) {
//        try {
//            String result = userSvc.upLoadImg(file);
//            if (result.equals("FAIL")){
//                return ResponseEntity.badRequest().body("이미지 저장을 실패하였습니다. (형식 에러 혹은 저장 실패 에러: jpg, png only)");
//            }
//            else{
//                return ResponseEntity.ok(result);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("이미지 저장시 에러가 발생했습니다.");
//        }
//    }
}
