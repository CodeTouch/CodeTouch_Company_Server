package com.tagmaster.codetouch.controller;

import com.tagmaster.codetouch.util.FileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/회사")
public class UploadImageCtrl {

    @PostMapping("/이미지저장")
    public ResponseEntity<String> imageSave(@RequestParam MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String splitFile = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                splitFile = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }

            if (!splitFile.equalsIgnoreCase("jpg") && !splitFile.equalsIgnoreCase("png")) {
                return ResponseEntity.badRequest().body("이미지 형식이 맞지 않습니다. (jpg, png only)");
            }

// 확장자 검증 후 저장 수행
            String image = FileUtil.imageSave(file);
            if (image == null) {
                return ResponseEntity.badRequest().body("이미지 저장을 실패하였습니다. 다시 시도해주세요.");
            }
            return ResponseEntity.ok(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이미지 저장시 에러가 발생했습니다.");
        }
    }
}
