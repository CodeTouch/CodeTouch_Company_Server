package com.tagmaster.codetouch.service.identity;

public interface AuthSvc {
    //APISignupDTO AuthReqService(String imp_uid, String accessToken) throws Exception;
    <T> T AuthReqService(Class<T> type, String imp_uid, String accessToken);
}
