package com.tagmaster.codetouch.repository.company;

import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.company.Payment;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    List<Payment> findByUserOrderByCreateAtDesc(CompanyUser user);
    List<Payment> findTop3ByUserOrderByCreateAtDesc(CompanyUser user);

}
