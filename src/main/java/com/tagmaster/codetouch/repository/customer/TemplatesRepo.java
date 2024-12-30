package com.tagmaster.codetouch.repository.customer;

import com.tagmaster.codetouch.entity.customer.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatesRepo extends JpaRepository<Templates,Integer> {

}
