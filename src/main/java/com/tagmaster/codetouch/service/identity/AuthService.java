package com.tagmaster.codetouch.service.identity;

import com.tagmaster.codetouch.dto.APISignupDTO;

public interface AuthService {
    String TokenService() throws Exception;
    //APISignupDTO AuthReqService(String imp_uid, String accessToken) throws Exception;
    <T> T AuthReqService(Class<T> type, String imp_uid, String accessToken) throws Exception;
}
