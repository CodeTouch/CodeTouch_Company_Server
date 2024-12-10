package com.tagmaster.codetouch.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDTO {
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phone;
    private String birth;
    private Integer gender;
}
