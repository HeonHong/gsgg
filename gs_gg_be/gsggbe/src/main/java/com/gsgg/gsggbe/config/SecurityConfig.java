package com.gsgg.gsggbe.config;

import com.gsgg.gsggbe.login.jwt.JWTUtil;
import com.gsgg.gsggbe.login.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }


    //비밀번호 해시로 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //jwt방식이라서 필요 없는 것들 끄기
        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        //form 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());
        //http basic 인증방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        //루트 설정
                        .requestMatchers("/login", "/join", "/admin", "/login/kakao/auth").permitAll()
                        //사용자 권한별 경로 설정
                        .requestMatchers("/admin").hasRole("ADMIN")
                        //다른 api 호출은 인가된 사람만 들어갈 수 있게 설정(테스트 후 경로별 변경 필요)
                        .anyRequest().authenticated()
                );

        //커스터마이즈 된 로그인 필터
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        //세션 설정
        //jwt방식이라서 stateless로 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //빌더 타입 반환
        return http.build();
    }
}
