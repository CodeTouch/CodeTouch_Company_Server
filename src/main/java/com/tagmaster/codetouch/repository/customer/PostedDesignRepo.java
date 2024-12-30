package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.PostedDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostedDesignRepo extends JpaRepository<PostedDesign, Integer> {

}
