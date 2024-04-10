package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.KakaoToken;
import com.gsgg.gsggbe.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token

    @Autowired
    private LoginService loginService;

    @PostMapping("/login/kakao/auth")
    public KakaoToken KakaoAuth(@RequestBody String code){
        System.out.println(code);
        KakaoToken kakaoToken = null;
        try {
            kakaoToken = loginService.getToken(code);
            loginService.getMebrInfo(kakaoToken.getAccessToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return kakaoToken;
    }
}
