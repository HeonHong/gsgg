package com.gsgg.gsggbe.config;

import com.gsgg.gsggbe.login.jwt.JWTFilter;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//스프링 시큐리티의 인가 및 설정 클래스
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //LoginFilter에서 authenticationManager가 생성자로 받을 객체
    //AuthenticationManager를 커스터마이즈 하기위해 필요
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    //AuthenticationConfiguration은 Spring Security의 자동구성 기능에 의해 생성
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }


    //비밀번호 해시로 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        생성자가 없이 만들었기 때문에 결과적으로 아래 생성자를 호출하게 됨.
//        public BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion version, int strength, SecureRandom random)
//        파라미터는 아래와 같이 설정 됨.
//        public BCryptPasswordEncoder( $2a , 10, null) {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    //코드 중복과 유지보수성을 줄여 재사용성 높이기 위해 선언
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    //vue단에서 처리안했으면 WebMVCConfigurer도 설정 필요
    //https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //httpServletRequest에 대해 일치하는지 판단하여 해당 요청에 필터 적용 여부 호가인
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //HttpSecurity객체는 웹 기반 보안에 대한 구성을 위한 메소드 제공
        //보안, 로그인, 로그아웃 등

        //cors설정
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //csrf disable
        //JWT는 사용자의 세션 상태를 클라이언트에서 관리 > 서버측에서 세션 상태 유지x
        //CSRF(Cross-Site Request Forgery)는 세션 쿠키를 사용하기 때문에 삭제
        http
                .csrf((auth) -> auth.disable());
        //form 로그인 방식 disable
        //Spring Security에서 기본으로 제공하는 로그인 폼 해제
        //아래에 커스터마이즈 된 로그인 폼 사용
        http
                .formLogin((auth) -> auth.disable());
        //HTTP Basic Authentication 방식 disable
        //JWT로 인증을 하기 때문에 필요 없음
        //해당 기능은 Authorization헤더에 인증정보를 Base64로 디코딩하여 기준으로 확인
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업 설정
        http
                .authorizeHttpRequests((auth)->auth
                        //인증이 필요한 경우 아래와 같이 작성
                        //.requestMatchers("/admin").authenticated()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().permitAll()
                );
        //JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        //커스터마이즈 된 로그인 필터
        //로그인 시 DB에 저장된 값과 비교하여 검증
        //Form Login기능을 사용할 경우, UsernamePasswordAuthenticationFilter가 자동으로 적용된다
        //커스터마이즈된 로그인 필터를 사용하는 경우, 해당 클래스를 UsernamePasswordAuthenticationFilter에 추가해 줘야한다
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정
        //JWT방식이라서 stateless로 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //빌더 타입 반환
        return http.build();
    }
}
