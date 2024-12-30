package com.tagmaster.codetouch.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CreateSiteDTO {
    private int templateId;
    private String email;
    private String siteName;
    private String url;
}
