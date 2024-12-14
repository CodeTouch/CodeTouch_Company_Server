package com.tagmaster.codetouch.repository.company;


import com.tagmaster.codetouch.entity.company.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUserRepo extends JpaRepository<CompanyUser, Integer> {
    Boolean existsByEmail(String email);
    CompanyUser findByEmail(String email);
    CompanyUser findByPhone(String phone);

    String findAllById(CompanyUser user);
}
