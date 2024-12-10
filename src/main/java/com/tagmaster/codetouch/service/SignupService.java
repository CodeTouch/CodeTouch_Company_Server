package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.SignupDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepository;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SignupService {
    private final CompanyUserRepository companyUserRepository;
    private final CustomerUserRepository customerUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignupService(
            CompanyUserRepository companyUserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            CustomerUserRepository customerUserRepository
    ) {
        this.companyUserRepository = companyUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerUserRepository = customerUserRepository;
    }

    @Transactional("chainedTransactionManager")
    public void SignupProcess(SignupDTO signupDTO) {
        String email = signupDTO.getEmail();

        Boolean isExist = companyUserRepository.existsByEmail(email);

        if (isExist) {

            return;
        }

        CompanyUser companyUser = new CompanyUser();

        companyUser.setNickname(signupDTO.getNickname());
        companyUser.setPassword(bCryptPasswordEncoder.encode(signupDTO.getPassword()));
        companyUser.setName(signupDTO.getName());
        companyUser.setPhone(signupDTO.getPhone());
        companyUser.setEmail(email);
        companyUser.setGender(signupDTO.getGender());
        companyUser.setBirth(LocalDate.parse(signupDTO.getBirth()));

        CustomerUser customerUser = new CustomerUser();
        customerUser.setNickname(signupDTO.getNickname());
        customerUser.setPassword(bCryptPasswordEncoder.encode(signupDTO.getPassword()));
        customerUser.setName(signupDTO.getName());
        customerUser.setPhone(signupDTO.getPhone());
        customerUser.setEmail(email);
        customerUser.setGender(signupDTO.getGender());
        customerUser.setBirth(LocalDate.parse(signupDTO.getBirth()));

        companyUserRepository.save(companyUser);
        customerUserRepository.save(customerUser);
    }

}
