package com.tagmaster.codetouch.dto.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDTO {
    private String email;
    private String name;
    private String phone;
    private String nickname;
    private String password;
    private String birth;
    private Integer gender;
    private Integer agree;
}
