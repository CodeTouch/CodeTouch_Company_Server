package com.tagmaster.codetouch.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class FileUtil {
    private static final String UPLOAD_DIR = "/Users/heejunida/Desktop/FinalProject/codetouch_Company_Server/src/main/resources/static";
    public static String imageSave(MultipartFile file){
        if(file.isEmpty()){
            return null;
        }
        try {
            File uploadDir = new File(UPLOAD_DIR + "/images");
            if(!uploadDir.exists()){
                uploadDir.mkdirs();
            }

            String newFileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            System.out.println(newFileName);
            File dest = new File(uploadDir, newFileName);
            file.transferTo(dest);
            return newFileName;
        } catch (Exception e) {
            return null;
        }
    }
}
