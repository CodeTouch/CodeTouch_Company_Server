//package com.tagmaster.codetouch;
//
//import com.tagmaster.codetouch.entity.company.CompanyUser;
//import com.tagmaster.codetouch.entity.customer.CustomerUser;
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
//public class UserRepositoryTest {
//
//    @Autowired
//    private CustomerUserRepository customerUserRepository;
//    @Autowired
//    private CompanyUserRepository companyUserRepository;
//    @Qualifier("chainedTransactionManager")
//    @Autowired
//    private PlatformTransactionManager chainedTransactionManager;
//
//
//    @Test
//    @Transactional("chainedTransactionManager")
//    @Rollback(false)
//    public void create() {
//        CompanyUser companyUser = new CompanyUser();
//        companyUser.setEmail("heejunidaaaa@gmail.com");
//        companyUser.setPassword("123456");
//        companyUser.setNickname("hehe");
//        companyUser.setName("장희준");
//        companyUser.setPhone("01011112222");
//        companyUser.setBirth(LocalDateTime.of(2003,4,23,0,0,0));
//        companyUser.setGender(3);
//
//        CustomerUser customerUser = new CustomerUser();
//        customerUser.setEmail("heejunidaaaa@gmail.com");
//        customerUser.setPassword("123456");
//        customerUser.setNickname("hehe");
//        customerUser.setName("장희준");
//        customerUser.setPhone("01011112222");
//        customerUser.setBirth(LocalDateTime.of(2003,4,23,0,0,0));
//        customerUser.setGender(3);
//
//        companyUserRepository.save(companyUser);
//        customerUserRepository.save(customerUser);
//    }
////
////    @Test
////    public void findAll() {
////        List<User> users = userRepository.findAll();
////        System.out.println(users);
////    }
////
////    @Test
////    @Transactional
////    @Rollback(false)
////    public void editNameAndGender(){
////        int id = 3;
////        User user = userRepository.findById(id).get();
////        user.setName("응애");
////        user.setGender(4);
////        userRepository.save(user);
////    }
////
////    @Test
////    @Transactional
////    @Rollback(false)
////    public void delete() {
////        userRepository.deleteById(2);
////    }
////
////    @Test
////    @Transactional("chainedTransactionManager")
////    @Rollback(false)
////    public void register(){
////        User user = new User();
////        user.setId("taeseongida");
////        user.setPassword("123456");
////        user.setEmail("haja1@gmail.com");
////        userRepository.save(user);
////        userRepository.save(user);
////    }
//}
