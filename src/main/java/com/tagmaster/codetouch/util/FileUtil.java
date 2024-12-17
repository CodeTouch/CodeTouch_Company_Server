package com.tagmaster.codetouch.util;

import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtil {
    private static final String UPLOAD_DIR = "/Users/heejunida/Desktop/FinalProject/codetouch_Company_Server/src/main/resources/static";
    private final CustomerUserRepo customerUserRepo;
    public FileUtil(CustomerUserRepo customerUserRepo) {
        this.customerUserRepo = customerUserRepo;
    }
    public static String imageSave(MultipartFile file){
        if(file.isEmpty()){
            return null;
        }
        try {
            File uploadDir = new File(UPLOAD_DIR + "/images");
            if(!uploadDir.exists()){
                uploadDir.mkdirs();
            }

            String newFileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            System.out.println(newFileName);

            File dest = new File(uploadDir, newFileName);
//            file.transferTo(dest);
            try (FileOutputStream fos = new FileOutputStream(dest)) {
                fos.write(file.getBytes());
            }
            String imgUrl = "http://192.168.5.10:8888/images/" + newFileName;
            // 저장된 파일의 URL 생성
            return imgUrl;
        } catch (Exception e) {
            return null;
        }
    }
}
