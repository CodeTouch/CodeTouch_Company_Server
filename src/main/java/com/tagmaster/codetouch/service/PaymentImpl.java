package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.company.Payment;
import com.tagmaster.codetouch.entity.customer.CustomerUser;
import com.tagmaster.codetouch.entity.customer.Site;
import com.tagmaster.codetouch.repository.company.CompanyUserRepository;
import com.tagmaster.codetouch.repository.company.PaymentRepository;
import com.tagmaster.codetouch.repository.customer.CustomerSiteRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentImpl implements PaymentSvc {
    private final CustomerUserRepository customerUserRepository;
    private final PaymentRepository paymentRepository;
    private final CompanyUserRepository companyUserRepository;
    private final CustomerSiteRepo customerSiteRepo;

    @Autowired
    public PaymentImpl(PaymentRepository paymentRepository,
                       CompanyUserRepository companyUserRepository,
                       CustomerUserRepository customerUserRepository,
                       CustomerSiteRepo customerSiteRepo) {
        this.paymentRepository = paymentRepository;
        this.companyUserRepository = companyUserRepository;
        this.customerUserRepository = customerUserRepository;
        this.customerSiteRepo = customerSiteRepo;
    }

    @Override
    public String Save(PaymentDTO paymentDTO) {
        try {
            CompanyUser user = companyUserRepository.findByEmail(paymentDTO.getUserEmail());
            if(user == null) {
                return "에러";
            }
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setSiteId(paymentDTO.getSiteId());
            paymentRepository.save(payment);

            return "성공";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    @Transactional
    public String UpgradeSite(PaymentDTO paymentDTO) {
        try {
            CustomerUser user = customerUserRepository.findByEmailAndSiteId(paymentDTO.getUserEmail(), paymentDTO.getSiteId());
            if (user == null) {
                return "에러";
            }
            Site site = customerSiteRepo.findById(paymentDTO.getSiteId()).get();
            site.setExpiry(paymentDTO.getExpiryDate());
            site.setPayState(1);
            customerSiteRepo.save(site);
            return "성공";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
