package com.tagmaster.codetouch.repository.company;

import com.tagmaster.codetouch.entity.company.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
}
