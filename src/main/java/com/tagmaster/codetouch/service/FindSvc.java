package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.PwChangeDTO;
import com.tagmaster.codetouch.dto.PwFindDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindSvc {
    private final CompanyUserRepo companyUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerUserRepo customerUserRepository;

    public FindSvc(CompanyUserRepo companyUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CustomerUserRepo customerUserRepository) {
        this.companyUserRepository = companyUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerUserRepository = customerUserRepository;
    }

    public String findPw(PwFindDTO pwFindDTO) {
        CompanyUser companyUserEmail = companyUserRepository.findByEmail(pwFindDTO.getEmail());
        CompanyUser companyUserPhone = companyUserRepository.findByPhone(pwFindDTO.getPhone());
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

    @Transactional("chainedTransactionManager")
    public String changePw(PwChangeDTO pwChangeDTO) {
        CompanyUser companyUser = companyUserRepository.findByEmail(pwChangeDTO.getEmail());
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
}
