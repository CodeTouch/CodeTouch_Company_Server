package com.tagmaster.codetouch.service.identity;

import com.tagmaster.codetouch.dto.APIPhoneDTO;
import com.tagmaster.codetouch.dto.APISignupDTO;
import com.tagmaster.codetouch.dto.PwFindDTO;
import com.tagmaster.codetouch.util.DateAndGenderChange;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AuthReqSvcImpl implements AuthSvc {

    @Override
    public <T> T AuthReqService(Class<T> type, String imp_uid, String accessToken) {
        try {
            String certificationUrl = "https://api.iamport.kr/certifications/" + imp_uid;

            URL urlToCertification = new URL(certificationUrl);
            HttpURLConnection certificationConn = (HttpURLConnection) urlToCertification.openConnection();

            certificationConn.setRequestMethod("GET");
            certificationConn.setRequestProperty("Authorization", "Bearer " + accessToken);
            certificationConn.setRequestProperty("Accept", "application/json");

            int responseCode = certificationConn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(certificationConn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONObject data = (JSONObject) jsonObject.get("response");

                System.out.println(data.toString());
                if (type == APISignupDTO.class) {
                    APISignupDTO dto = new APISignupDTO();
                    dto.setName(data.getString("name"));
                    dto.setPhone(data.getString("phone"));
                    dto.setBirth(data.getString("birthday"));
                    dto.setGender(DateAndGenderChange.genderChange(dto.getBirth(), data.getString("gender")));

                    return type.cast(dto);
                } else if (type == APIPhoneDTO.class) {
                    APIPhoneDTO phone = new APIPhoneDTO();
                    phone.setPhone(data.getString("phone"));

                    return type.cast(phone);
                } else if (type == PwFindDTO.class) {
                    PwFindDTO dto = new PwFindDTO();
                    dto.setName(data.getString("name"));
                    dto.setPhone(data.getString("phone"));

                    return type.cast(dto);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}


