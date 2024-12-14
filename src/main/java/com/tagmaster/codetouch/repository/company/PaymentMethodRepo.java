package com.tagmaster.codetouch.repository.company;

import com.tagmaster.codetouch.entity.company.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Integer> {

}
