package com.tagmaster.codetouch.dto.company;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChangeInfoDTO {
    private String email;
    private String nickname;
    private String name;
    private String phone;
    private MultipartFile updateImage;
    private String updateImageName;
}
