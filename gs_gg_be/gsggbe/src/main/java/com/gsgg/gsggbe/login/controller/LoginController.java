package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.KakaoToken;
import com.gsgg.gsggbe.login.dto.KakaoUserInfo;
import com.gsgg.gsggbe.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token

    @Autowired
    private LoginService loginService;

    @PostMapping("/kakao/auth")
    public KakaoUserInfo KakaoAuth(@RequestBody String code) {
        boolean isMember = false;
        if (code.equals(null)) {
            log.info("카카오 토큰 확인용 인가 코드 없음");
        }
        String accessToken = loginService.getToken(code);
        KakaoUserInfo userInfo = loginService.getMebrInfo(accessToken);
        return userInfo;
    }
}
