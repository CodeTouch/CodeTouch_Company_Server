package com.tagmaster.codetouch.service.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagmaster.codetouch.dto.APIPaymentDTO;
import com.tagmaster.codetouch.util.TokenUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class GetPaymentSvc{
    public APIPaymentDTO APIPayment(String merchantId){
        APIPaymentDTO apiPaymentDTO = new APIPaymentDTO();

        RestTemplate restTemplate = new RestTemplate();
        String paymentUrl = "https://api.iamport.kr/payments/find/" + merchantId;

        String accessToken = TokenUtil.TokenUtil();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<Map> response = restTemplate.exchange(paymentUrl, HttpMethod.GET, request, Map.class);

        Object responObject = response.getBody().get("response");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseData = mapper.convertValue(responObject, Map.class);
        Integer amount = (Integer) responseData.get("amount");
        String paymentMethod = (String) responseData.get("pay_method");

        apiPaymentDTO.setAmount(amount);
        apiPaymentDTO.setPayMethod(paymentMethod);
        // 결제 정보 반환
        return apiPaymentDTO;

    }
}
