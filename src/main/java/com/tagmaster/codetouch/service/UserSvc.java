package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.APIPhoneDTO;
import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.PwFindDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import com.tagmaster.codetouch.service.identity.AuthSvc;
import com.tagmaster.codetouch.util.TokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class UserSvc {
    private final CompanyUserRepo companyUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerUserRepo customerUserRepository;
    private final AuthSvc authSvc;

    public UserSvc(CompanyUserRepo companyUserRepo, BCryptPasswordEncoder bCryptPasswordEncoder, CustomerUserRepo customerUserRepository, AuthSvc authSvc) {
        this.companyUserRepo = companyUserRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerUserRepository = customerUserRepository;
        this.authSvc = authSvc;
    }

    public String findPw(PwFindDTO pwFindDTO) {

        CompanyUser companyUserEmail = companyUserRepo.findByEmail(pwFindDTO.getEmail());
        CompanyUser companyUserPhone = companyUserRepo.findByPhone(pwFindDTO.getPhone());
        try {
            if (companyUserEmail == null || companyUserPhone == null) {
                return "FAIL";
            }
            int emailId = companyUserEmail.getUserId();
            int phoneId = companyUserPhone.getUserId();

            if (emailId != phoneId) {
                return "FAIL";
            }
            return "SUCCESS";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    @Transactional/*("chainedTransactionManager")*/
    public String changePw(PwChangeDTO pwChangeDTO) {
        CompanyUser companyUser = companyUserRepo.findByEmail(pwChangeDTO.getEmail());
        try {
            if (companyUser == null) {
                return "FAIL";
            }
            String newPassword = pwChangeDTO.getPassword();
            companyUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
            List<CustomerUser> customerUser = customerUserRepository.findByEmail(pwChangeDTO.getEmail());
            for (CustomerUser user : customerUser) {
                String customerRole = user.getRole();
                if (customerRole.equals("USER,ADMIN")) {
                    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                    customerUserRepository.save(user);
                }
            }
            return "SUCCESS";
        } catch (Exception e) {
            return "ERROR";
        }
    }
    public String emailFind(String imp_uid) {
        try {
            if (imp_uid == null) {
                return "FAIL";
            }
            String accessToken = TokenUtil.TokenUtil();
            APIPhoneDTO dto = authSvc.AuthReqService(APIPhoneDTO.class, imp_uid, accessToken);
            CompanyUser user = companyUserRepo.findByPhone(dto.getPhone());
            return user.getEmail();
        } catch (Exception e) {
            return "ERROR";
        }
    }

    public Boolean isPwCorrect(PwChangeDTO pwChangeDTO) {
        CompanyUser companyUser = companyUserRepo.findByEmail(pwChangeDTO.getEmail());
        if (companyUser == null) {
            return false;
        }
        String oldPassword = companyUser.getPassword();
        String password = pwChangeDTO.getPassword();
        return bCryptPasswordEncoder.matches(password, oldPassword);
    }
}
