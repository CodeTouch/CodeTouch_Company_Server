package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepo extends JpaRepository<Design, Integer> {

}
