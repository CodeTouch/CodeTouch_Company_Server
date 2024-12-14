package com.tagmaster.codetouch.service;

import com.tagmaster.codetouch.dto.PayReadDTO;
import com.tagmaster.codetouch.dto.PaymentDTO;
import com.tagmaster.codetouch.entity.company.CompanyUser;
import com.tagmaster.codetouch.entity.company.Payment;
import com.tagmaster.codetouch.entity.customer.CustomerUser;

import com.tagmaster.codetouch.entity.customer.Site;
import com.tagmaster.codetouch.repository.company.CompanyUserRepo;
import com.tagmaster.codetouch.repository.company.PaymentRepo;
import com.tagmaster.codetouch.repository.customer.CustomerSiteRepo;
import com.tagmaster.codetouch.repository.customer.CustomerUserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentImpl implements PaymentSvc {
    private final CustomerUserRepo customerUserRepository;
    private final PaymentRepo paymentRepository;
    private final CompanyUserRepo companyUserRepository;
    private final CustomerSiteRepo customerSiteRepo;
    private final PaymentRepo paymentRepo;

    @Autowired
    public PaymentImpl(PaymentRepo paymentRepository,
                       CompanyUserRepo companyUserRepository,
                       CustomerUserRepo customerUserRepository,
                       CustomerSiteRepo customerSiteRepo,
                       PaymentRepo paymentRepo) {
        this.paymentRepository = paymentRepository;
        this.companyUserRepository = companyUserRepository;
        this.customerUserRepository = customerUserRepository;
        this.customerSiteRepo = customerSiteRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    public String Save(PaymentDTO paymentDTO) {
        try {
            CompanyUser user = companyUserRepository.findByEmail(paymentDTO.getEmail());
            if (user == null) {
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
            CustomerUser user = customerUserRepository.findByEmailAndSiteId(paymentDTO.getEmail(), paymentDTO.getSiteId());
            if (user == null) {
                return "에러";
            }
            Site site = customerSiteRepo.findById(paymentDTO.getSiteId()).get();
            site.setExpiry(paymentDTO.getExpiry());
            site.setPayState(1);
            customerSiteRepo.save(site);
            return "성공";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
//    public String Read(String email) {
//        try {
//            CompanyUser user = companyUserRepository.findByEmail(email);
//            if (user == null) {
//                return "에러";
//            }
//            List<PayReadDTO> receipt = new ArrayList<>();
//            List<CustomerUser> customerUsers = customerUserRepository.findByEmail(email);
//            for (CustomerUser customerUser : customerUsers) {
//                receipt.add(new PayReadDTO());
//                PayReadDTO payReadDTO = new PayReadDTO();
//                payReadDTO.setUser(user);
//                Site site = customerSiteRepo.findById(customerUser.getSiteId()).get();
//                payReadDTO.setSiteName(site.getSiteName());
//                Payment payment = paymentRepository.findByEmail(customerUser.getEmail());
//                payReadDTO.setMerchantId(payment.merchantId);
//                payReadDTO.setCreateAt(payment)
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    return null;
//    }
//}
