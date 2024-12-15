package com.tagmaster.codetouch.service.pay;

import com.tagmaster.codetouch.dto.APIPaymentDTO;
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
import com.tagmaster.codetouch.util.DateAndGenderChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "spring.datasource.customer", name = "enabled", havingValue = "true")
public class PaymentSvcImpl implements PaymentSvc {

    private final CustomerUserRepo customerUserRepository;
    private final CustomerSiteRepo customerSiteRepo;
    private final PaymentRepo paymentRepository;
    private final CompanyUserRepo companyUserRepository;
    private final GetPaymentSvc getPaymentSvc;

    @Autowired()
    public PaymentSvcImpl(PaymentRepo paymentRepository,
                          CompanyUserRepo companyUserRepository,
                          CustomerUserRepo customerUserRepository,
                          CustomerSiteRepo customerSiteRepo,
                          GetPaymentSvc getPaymentSvc) {
        this.paymentRepository = paymentRepository;
        this.companyUserRepository = companyUserRepository;
        this.customerUserRepository = customerUserRepository;
        this.customerSiteRepo = customerSiteRepo;
        this.getPaymentSvc = getPaymentSvc;
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
    @Transactional/*("chainedTransactionManager")*/
    public List<PayReadDTO> Read(String email) {
        //리스트 생성 (response 용)
        List<PayReadDTO> receipt = new ArrayList<>();
        try {
            // 브라우저에서 보내온 이메일 검증
            CompanyUser user = companyUserRepository.findByEmail(email);
            if (user == null) {
                return null;
            }

            List<Payment> paymentList = new ArrayList<>();
            boolean isAll = true;
            if (isAll){
                // 1번 전부를 요청했다.
                // Payment 에서 해당 이메일로 전부 끌어서 List<Payment> 에 담아준다.
                paymentList = paymentRepository.findByUserOrderByCreatedAtDesc(user);
            }else{
                // 2번 3개만 요청한다.
                // Payment 에서 해당 이메일로 3개만(createAt 가장 최근) List<Payment> 에 담아준다.
                paymentList = paymentRepository.findTop3ByUserOrderByCreatedAtDesc(user);
                // 3개를 레포지토리에서 가져온다.

            }

            // 검증( 가져왔는지 )
            if (paymentList == null || paymentList.isEmpty()) {
                return null;
            }

            for (Payment payment : paymentList) {
                PayReadDTO payReadDTO = new PayReadDTO();
                payReadDTO.setMerchantId(payment.getMerchantId());
                String createAtDate = DateAndGenderChange.DateTimeToDate(payment.getCreatedAt());
                payReadDTO.setCreateAt(createAtDate);

                APIPaymentDTO apiPaymentDTO = getPaymentSvc.GetPayment(payment.getMerchantId());
                payReadDTO.setPayMethod(apiPaymentDTO.getPayMethod());
                payReadDTO.setAmount(apiPaymentDTO.getAmount());

                Site site = customerSiteRepo.findById(payment.getSiteId()).get();
                payReadDTO.setExpiry(site.getExpiry());
                payReadDTO.setSiteName(site.getSiteName());
                payReadDTO.setPayState(site.getPayState());

                receipt.add(payReadDTO);
            }

            return receipt;
        } catch (Exception e) {
            return null;
        }
    }
}

