//package com.tagmaster.codetouch.service;
//
//import com.tagmaster.codetouch.entity.company.CompanyUser;
//import com.tagmaster.codetouch.repository.company.CompanyUserRepository;
//import org.springframework.stereotype.Service;
//
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//
//@Service
//public class ChangeDateAndGender {
//
//    public String dateChange(LocalDateTime birth) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        return format.format(birth);
//    }
//
//    public Integer genderChange(LocalDateTime birth, String gender){
//        String birthday = dateChange(birth);
//        birthday = birthday.substring(0, birthday.indexOf("-"));
//        String year = birthday.substring(birthday.length()-8, birthday.length()-4);
//        if (Integer.parseInt(year) > 1999) {
//            if (gender.equals("male")) {
//                return 3;
//            } else {
//                return 4;
//            }
//        } else {
//            if (gender.equals("male")) {
//                return 1;
//            } else {
//                return 2;
//            }
//        }
//    }
//}
