package com.gsgg.gsggbe.login.service;

import com.gsgg.gsggbe.login.dto.CustomUserDetails;
import com.gsgg.gsggbe.login.dto.UserDTO;
import com.gsgg.gsggbe.mapper.logIn.LogInMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/*
* AuthenticationProvier에 인증 요청이 올 때 실행.
* 1. 사용자가 로그인 요청을 보냄
* 2. AuthenticationFilter가 이 요청을 받아 UsernamePasswordAuthenticationToken을 생성(LoginFilter참고)
* 3. AuthenticationManager에게 위임(LoginFilter 참고)
* 4. AuthenticationManger가 AutheticationProvider를 선택하여 인증용 객체를 다시 전달
* */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final LogInMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        //여기서 아이디를 기준으로 검증
        //아이디가 있는데 아이디와 비밀번호가 맞지 않으면 exception 발생

        //정상 아이디면 LoginFilter(UsernamePasswordAuthenticationFilter)의 successfulAuthentication으로
        UserDTO userDTO =  loginMapper.findUser(username);

        //정상 아이디가 아닐 경우 unSuccessfulAuthentication
        if(userDTO ==null) throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
        return new CustomUserDetails(userDTO);

    }
}
