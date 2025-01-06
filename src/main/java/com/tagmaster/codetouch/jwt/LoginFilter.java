package com.tagmaster.codetouch.jwt;

import com.tagmaster.codetouch.dto.company.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.awt.*;
import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/고객/회원/로그인");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
        String requestBody;
        try {
            // 요청 본문(JSON)을 String으로 읽기
            requestBody = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read request body", e);
        }

        // JSONObject로 JSON 데이터 파싱
        JSONObject jsonObject = new JSONObject(requestBody);

        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        System.out.println(email + " 유저이메일");
        System.out.println(password + " 유저비번");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) {
        try {

            System.out.println(authentication+"진입");

            log.info("successful authentication 진입");
            log.info(String.valueOf(req));
            log.info(String.valueOf(res));
            log.info(String.valueOf(chain));
            log.info(String.valueOf(authentication));
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); //user 가져나오기
            String email = userDetails.getUsername();//username 뽑아내기
            String name = userDetails.getName();
            String imageUrl = userDetails.getImageUrl();
            String nickname = userDetails.getNickname();
            String phone = userDetails.getPhone();

//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//collection 에서 authority를 뽑아낸다
//            Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); //iterator 를 통해서 반복을 시켜서 내부 객체를 뽑아 낸다
//            GrantedAuthority auth = iterator.next();
            //role값 가져오기
//유저 이름 / 이미지 url / 닉네임 / 휴대폰번호
            //뽑아낸 username 과 role값을 가지고 jwtUtil에 토큰을 만들어 달라고 전달한다.
            String token = jwtUtil.createJwt(email,60*60L*1000);

            JSONObject responseJson = new JSONObject();
            responseJson.put("name", name);
            responseJson.put("imageUrl", imageUrl);
            responseJson.put("nickname", nickname);
            responseJson.put("phone", phone);

            res.addHeader("Authorization", "Bearer " + token);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(responseJson.toString());
            res.setStatus(HttpServletResponse.SC_OK);

            //인증방식을 붙이고 토큰 붙이고 사용 (필수) HTTP 인증 방식 -> rfc 7235 정의에 따라 인증 헤더 형태를 가져야 함
        }catch (Exception e){
            log.info("success 에러");
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
        String errResponse = "로그인에 실패하였습니다. 다시 시도해주세요.";
        res.setHeader("Content-Type", "text/plain");
        res.setCharacterEncoding("UTF-8");
        Window.getWindows();
        res.getWriter().write(errResponse);
        res.setStatus(400);
    }
}

