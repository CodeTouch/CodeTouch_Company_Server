package com.tagmaster.codetouch.dto;

import com.tagmaster.codetouch.entity.company.CompanyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final CompanyUser companyUser; //생성자 방 식으로 초기화해야함
    public CustomUserDetails(CompanyUser companyUser) {
        this.companyUser = companyUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//role값
        System.out.println("CustomUserDetails.getAuthorities() 진입");
        Collection<GrantedAuthority> collection = new ArrayList<>();
        return collection;
    }

    @Override
    public String getPassword() { //비밀번호값
        return companyUser.getPassword();
    }
    @Override
    public String getUsername() {
        return companyUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { //expire되었는지
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //lock 되었는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
