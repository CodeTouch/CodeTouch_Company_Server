package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.entity.customer.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSiteRepo extends JpaRepository<Site, Integer> {
}
