package com.tagmaster.codetouch.service.company;

import com.tagmaster.codetouch.dto.company.SignupDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SignupSvc {
    private final CompanyUserRepo companyUserRepository;
    private final CustomerUserRepo customerUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignupSvc(
            CompanyUserRepo companyUserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            CustomerUserRepo customerUserRepository
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
        customerUser.setRole("USER,ADMIN");

        companyUserRepository.save(companyUser);
        customerUserRepository.save(customerUser);
    }

    public Boolean isEmailAvailable(String email){
        return !companyUserRepository.existsByEmail(email);
    }
}
