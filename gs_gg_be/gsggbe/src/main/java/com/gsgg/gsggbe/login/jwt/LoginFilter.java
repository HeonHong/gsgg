package com.gsgg.gsggbe.login.jwt;

import com.gsgg.gsggbe.login.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/*
* UsernamePasswordAuthenticationFilter는 "/login"으로 들어오는 post요청 처리
* */

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    //인증처리용 객체
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 추출
        //관습적으로 ID를 Username으로 사용했기 때문에 UsernamePasswordAuthenticationFilter에 메소드 자체가 obtainUsername으로 되어있다
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        log.info("LoginFilter username {}", username);

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        //인증 처리를 위한 객체 반환
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        log.info("로그인 정보 조회 성공 {}", response);
        //인증된 주체(customUserDetails) 정보를 가지고 온다
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        //마지막은 유지 시간
        String token = jwtUtil.createJWT(username,role,60*60*10L);
        //Http인증방식은 RFC7235 정의에 따라 아래 인증 헤더 형태를 가져야 함.
        //Authorization은 타입 인증토큰
//        response.addHeader("Authorization","Bearer " + token);
//        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Bearer " + token);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        //여기에 찍히는 message는 CustomUserDetailsService에서 던진 예외 메시지가 아니다
        //attemptAuthentication에서 던진 메시지다.
        log.error(failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401에러
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(failed.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}