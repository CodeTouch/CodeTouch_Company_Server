package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerUserRepo extends JpaRepository<CustomerUser, Integer> {
    List<CustomerUser> findByEmailAndSiteId(String email, int siteId);
    List<CustomerUser> findByEmail(String email);
    List<CustomerUser> findBySiteId(int siteId);

}
