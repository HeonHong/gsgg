package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.KakaoToken;
import com.gsgg.gsggbe.login.dto.KakaoUserInfo;
import com.gsgg.gsggbe.login.service.CustomUserDetailsService;
import com.gsgg.gsggbe.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token

    private final LoginService loginService;

    private final CustomUserDetailsService customUserDetailsService;
    private String role;

    @PostMapping("/")
    public String main() {
        log.info("LoginController 들어오는지 확인하기");
        //세션 사용자 아이디 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("세션 사용자 아이디 확인하기 {}", username);

        //세션 사용자 Role확인하기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();
        log.info("세션 사용자 role확인하기 {}", role);
        return "Login Controller" + username + role;
    }
/*

    @PostMapping("/login")
    public void mainLogIn() {
        해당 컨트롤러는 필요없다.
        "/login"으로 된 post 요청이 들어 올 경우,
        LoginFilter(UsernamePasswordAuthenticationFilter)에서 가로챈다.
        그 다음 CustomUserDetailsService에서 확인한 뒤
        successfulAuthentication 혹은 unSuccessfulAuthentication 으로 값을 반환한다
    }
*/

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
