package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.CustomUserDetails;
import com.gsgg.gsggbe.login.dto.KakaoToken;
import com.gsgg.gsggbe.login.dto.KakaoUserInfo;
//import com.gsgg.gsggbe.login.service.CustomUserDetailsService;
import com.gsgg.gsggbe.login.jwt.JWTUtil;
import com.gsgg.gsggbe.login.service.CustomUserDetailsService;
import com.gsgg.gsggbe.login.service.LoginService;
import com.gsgg.gsggbe.response.BasicResponse;
import com.gsgg.gsggbe.response.ResponseType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token

    private final LoginService loginService;

    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;


    //    private final CustomUserDetailsService customUserDetailsService;
    private String role;

//    @PostMapping("/")
//    public String main() {
//        //세션 사용자 아이디 확인
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        log.info("세션 사용자 아이디 확인하기 {}", username);
//
//        //세션 사용자 Role확인하기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
//        log.info("세션 사용자 role확인하기 {}", role);
//        return "Login Controller" + username + role;
//    }
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
    public BasicResponse<?> KakaoAuth(@RequestBody String code) {
        boolean isMember = false;

        String accessToken = loginService.getToken(code);
        KakaoUserInfo userInfo = loginService.getKakaoUserInfo(accessToken);
        String username = loginService.getUserName(userInfo.getId());
        if (username==null) {
            log.info("/kakao/auth KakaoId Not Exist {} ", userInfo.getId());
            Map<String, String> err = new HashMap<>();
            err.put("message", "기존 정보와 연동된 정보가 없습니다. 회원 가입하시겠습니까?");
//            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(err);
            return BasicResponse.builder().responseType(ResponseType.LOGIN_NO_RELATED_ID).body(err).build();
        }

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        String token = jwtUtil.createJWT(username, role, 60 * 60 * 10L);

        return BasicResponse.builder().responseType(ResponseType.SUCCESS).body(token).build();
    }
}
