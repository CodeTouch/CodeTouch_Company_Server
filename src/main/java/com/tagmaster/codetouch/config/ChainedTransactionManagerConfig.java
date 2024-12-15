//package com.tagmaster.codetouch.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.transaction.ChainedTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//public class ChainedTransactionManagerConfig {
//
//    @Bean(name = "chainedTransactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("companyTransactionManager") PlatformTransactionManager companyTransactionManager,
//            @Qualifier("customerTransactionManager") PlatformTransactionManager customerTransactionManager) {
//        return new ChainedTransactionManager(companyTransactionManager, customerTransactionManager);
//    }
//}
