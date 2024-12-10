package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, Integer> {

}
