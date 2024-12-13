package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.entity.customer.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSiteRepo extends JpaRepository<Site, Integer> {
}
