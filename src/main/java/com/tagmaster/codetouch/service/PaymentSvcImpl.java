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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentSvcImpl implements PaymentSvc {

    private final CustomerUserRepo customerUserRepository;
    private final PaymentRepo paymentRepository;
    private final CompanyUserRepo companyUserRepository;
    private final CustomerSiteRepo customerSiteRepo;
    private final PaymentRepo paymentRepo;
    private final CustomerUserRepo customerUserRepo;

    @Autowired
    public PaymentSvcImpl(PaymentRepo paymentRepository,
                          CompanyUserRepo companyUserRepository,
                          CustomerUserRepo customerUserRepository,
                          CustomerSiteRepo customerSiteRepo,
                          PaymentRepo paymentRepo, CustomerUserRepo customerUserRepo) {
        this.paymentRepository = paymentRepository;
        this.companyUserRepository = companyUserRepository;
        this.customerUserRepository = customerUserRepository;
        this.customerSiteRepo = customerSiteRepo;
        this.paymentRepo = paymentRepo;
        this.customerUserRepo = customerUserRepo;
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

    @Override
    @Transactional("chainedTransactionManager")
    public List<PayReadDTO> Read(String email) {
        try {
            List<PayReadDTO> payReadDTOList = new ArrayList<>();
            CompanyUser user = companyUserRepository.findByEmail(email);
            List<CustomerUser> cu = customerUserRepo.findByEmail(email);
            // 사용자 권한 확인 (cus -> user_id, com -> user_id)
            List<Site> sites = customerSiteRepo.findByEmail(email); // cus -> List<Site>
            List<Payment> payments = paymentRepository.findByUser(user); // com -> List<Payment>
            // 두 리스트의 데이터를 비교 및 처리
            for (Payment payment : payments) {
                for (Site site : sites) {
                    if (payment.getSiteId() == (site.getSiteId())) { // site_id 매칭
                        PayReadDTO dto = new PayReadDTO();
                        dto.setSiteName(site.getSiteName());
                        dto.setMerchantId(payment.getMerchantId());
                        dto.setCreateAt(payment.getCreatedAt());
                        payReadDTOList.add(dto);
                        break; // 매칭 후 내부 루프 종료
                    }
                }
            }
            return payReadDTOList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

