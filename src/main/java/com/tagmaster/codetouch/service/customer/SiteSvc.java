package com.tagmaster.codetouch.service.customer;


import com.tagmaster.codetouch.dto.CreateSiteDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.entity.customer.Site;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.repository.customer.CustomerSiteRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class SiteSvc {
    private final CompanyUserRepo companyUserRepo;
    private final CustomerUserRepo customerUserRepo;
    private final CustomerSiteRepo customerSiteRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SiteSvc(CompanyUserRepo companyUserRepo,
                   CustomerUserRepo customerUserRepo,
                   CustomerSiteRepo customerSiteRepo,
                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.companyUserRepo = companyUserRepo;
        this.customerUserRepo = customerUserRepo;
        this.customerSiteRepo = customerSiteRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean siteCreate(CreateSiteDTO createSiteDTO) {
        try {
            CompanyUser user = companyUserRepo.findByEmail(createSiteDTO.getEmail());
            if (user == null) {
                return false;
            }
            // 커스터머 유저에 컴퍼니 기본 정보 넣어주기
            CustomerUser customerUser = new CustomerUser();
            customerUser.setNickname(user.getNickname());
            customerUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            customerUser.setName(user.getName());
            customerUser.setPhone(user.getPhone());
            customerUser.setEmail(user.getEmail());
            customerUser.setGender(user.getGender());
            customerUser.setBirth((user.getBirth()));
            customerUser.setAgree(1);
            customerUser.setRole("USER,ADMIN");
            customerUserRepo.save(customerUser);
            //처음에 넣어주고
            try {
                try {
                    String url = createSiteDTO.getUrl();
                    String realUrl = url + ".코드터치.한글";

                // 사이트 생성
                Site site = new Site();
                site.setSiteName(createSiteDTO.getSiteName());
                site.setUserId(customerUser);
                site.setPayState(0);
                site.setUrl(realUrl);
                customerSiteRepo.save(site);

                customerUser.setSiteId(site.getSiteId());
                customerUserRepo.save(customerUser);
                // 사이트 생성이후
                // 위에 커스터머 유저에 사이트 주입
                // 커스터머 유저 세이브
                    return true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    customerUserRepo.delete(customerUser);
                    return false;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Site> siteRead(String email) {
        List<Site> sites = new ArrayList<>();
        CompanyUser companyUser = companyUserRepo.findByEmail(email);
        if (companyUser == null) {
            return null;
        }

        List<CustomerUser> customerUser = customerUserRepo.findByEmail(companyUser.getEmail()) ;
        for (CustomerUser cu : customerUser) {
//            Site site = customerSiteRepo.findByDeletedAtIsNullAndSiteId(cu.getSiteId());

            if (!cu.getRole().equals("USER,ADMIN")) {
                continue;
            }
            Site site = customerSiteRepo.findByIsDeleteAndSiteId(0, cu.getSiteId());
            if (site != null) {
                sites.add(site);
            }
        }
        return sites;
    }


//            if(site != null){
//                sites.add(site);
//            }



    public boolean siteDelete(String email, int siteId) {
        try {
            CompanyUser companyUser = companyUserRepo.findByEmail(email);
            if (companyUser == null) {
                return false;
            }
//            Site site = customerSiteRepo.findByDeletedAtIsNullAndSiteId(siteId);
//            customerSiteRepo.deleteById(site.getSiteId());
            Site site = customerSiteRepo.findById(siteId).get();
            site.setIsDelete(1);
            customerSiteRepo.save(site);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

