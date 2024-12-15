package com.tagmaster.codetouch.service.pay;

import com.tagmaster.codetouch.dto.APIPaymentDTO;
import com.tagmaster.codetouch.util.TokenUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GetPaymentSvc{
    public APIPaymentDTO GetPayment(String merchantId){
        APIPaymentDTO apiPaymentDTO = new APIPaymentDTO();

        RestTemplate restTemplate = new RestTemplate();
        String paymentUrl = "https://api.iamport.kr/payments/" + merchantId;

        String accessToken = TokenUtil.TokenUtil();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<Map> response = restTemplate.exchange(paymentUrl, HttpMethod.GET, request, Map.class);

        apiPaymentDTO.setAmount((Integer) response.getBody().get("amount"));
        apiPaymentDTO.setPayMethod((String) response.getBody().get("pay_method"));
        // 결제 정보 반환
        return apiPaymentDTO;

    }
}
