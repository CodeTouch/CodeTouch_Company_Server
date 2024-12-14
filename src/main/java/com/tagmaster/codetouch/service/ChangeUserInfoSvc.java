package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.company.ChangeInfoDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ChangeUserInfoSvc {
    private final CompanyUserRepo companyUserRepo;

    @Autowired
    public ChangeUserInfoSvc(CompanyUserRepo companyUserRepo) {
        this.companyUserRepo = companyUserRepo;
    }

    public Boolean changeUserInfo(ChangeInfoDTO changeInfoDTO) {
        try {
            CompanyUser companyUser = companyUserRepo.findByEmail(changeInfoDTO.getEmail());
            if (companyUser == null) {
                return false;
            }
            companyUser.setNickname(changeInfoDTO.getNickname());
            companyUser.setPhone(changeInfoDTO.getPhone());
            MultipartFile file = changeInfoDTO.getUpdateImage();
            String originalFilename = file.getOriginalFilename();
            String splitFile = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                splitFile = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }

            if (!splitFile.equalsIgnoreCase("jpg") && !splitFile.equalsIgnoreCase("png")) {
                return false;
            }

// 확장자 검증 후 저장 수행
            String image = FileUtil.imageSave(file);
            if (image == null) {
                return false;
            }
            companyUser.setImageUrl(image);
            companyUserRepo.save(companyUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
