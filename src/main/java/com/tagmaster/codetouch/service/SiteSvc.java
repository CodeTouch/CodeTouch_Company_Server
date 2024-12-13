package com.tagmaster.codetouch.service;


import com.tagmaster.codetouch.dto.CreateSiteDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.entity.customer.Site;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.repository.customer.CustomerSiteRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteSvc {
    private final CompanyUserRepo companyUserRepo;
    private final CustomerUserRepo customerUserRepo;
    private final CustomerSiteRepo customerSiteRepo;

    public SiteSvc(CompanyUserRepo companyUserRepo, CustomerUserRepo customerUserRepo, CustomerSiteRepo customerSiteRepo) {
        this.companyUserRepo = companyUserRepo;
        this.customerUserRepo = customerUserRepo;
        this.customerSiteRepo = customerSiteRepo;
    }
    public Site siteCreate(CreateSiteDTO createSiteDTO) {
        CompanyUser user = companyUserRepo.findByEmail(createSiteDTO.getEmail());
        if (user == null) {
            return null;
        }
        List<CustomerUser> customerUser = customerUserRepo.findByEmail(user.getEmail());
        for (CustomerUser cu : customerUser) {
            Site site = new Site();
            site.setSiteName(createSiteDTO.getSiteName());
            site.setUserId(cu);
            site.setPayState(0);
            customerSiteRepo.save(site);
        }
        return new Site();
    }

    public List<Site> siteRead(String email) {
        List<Site> sites = new ArrayList<>();
        CompanyUser companyUser = companyUserRepo.findByEmail(email);
        if (companyUser == null) {
            return null;
        }

        List<CustomerUser> customerUser = customerUserRepo.findByEmail(companyUser.getEmail());
        for (CustomerUser cu : customerUser) {
            if (!cu.getRole().equals("USER,ADMIN")) {
                return null;
            }
            Site site = customerSiteRepo.findById(cu.getSiteId()).get();
            sites.add(site);
        }
        return sites;
    }

    public boolean siteDelete(String email, int siteId) {
        try {
            CompanyUser companyUser = companyUserRepo.findByEmail(email);
            if (companyUser == null) {
                return false;
            }
            List<CustomerUser> customerUser = customerUserRepo.findByEmail(companyUser.getEmail());
            Site site = customerSiteRepo.findById(siteId).get();
            for (CustomerUser cu : customerUser) {
                if (!cu.getRole().equals("USER,ADMIN")) {
                    return false;
                }
                if (cu.getSiteId() == siteId) {
                    customerSiteRepo.delete(site);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

