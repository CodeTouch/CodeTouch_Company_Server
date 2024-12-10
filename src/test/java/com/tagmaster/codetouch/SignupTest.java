//package com.tagmaster.codetouch;
//
//import com.tagmaster.codetouch.entity.company.CompanyUser;
//import com.tagmaster.codetouch.repository.company.CompanyUserRepository;
//import com.tagmaster.codetouch.repository.customer.CustomerUserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@SpringBootTest
//public class SignupTest {
//
//    @Autowired
//    private CustomerUserRepository customerUserRepository;
//
//    @Autowired
//    private PlatformTransactionManager chainedTransactionManager;
//    @Autowired
//    private CompanyUserRepository companyUserRepository;
//
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    public void signup() {
//        CompanyUser companyUser = new CompanyUser();
//        companyUser.setEmail("히힛");
//        companyUser.setName("응");
//        companyUser.setPassword("123456");
//        companyUser.setPhone("01099998888");
//        companyUser.setBirth(LocalDateTime.of(2001,1,13,0,0,0));
//        companyUser.setGender(2);
//        companyUserRepository.save(companyUser);
//    }
//
//}
