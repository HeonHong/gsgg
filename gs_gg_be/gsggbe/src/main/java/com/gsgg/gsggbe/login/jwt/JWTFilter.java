package com.gsgg.gsggbe.login.jwt;

import com.gsgg.gsggbe.login.dto.CustomUserDetails;
import com.gsgg.gsggbe.login.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//인가 필요한 경우 JWT검증하는 필터
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        //Authorization Header 검증
        if(authorization == null || !authorization.startsWith("Bearer")){
            //다음 필터로 넘기기
            //이제 로그인을 해야하는 경우가 될 수도 있고,
            //혹은 정보가 탈취되어 조작되었을 수도 있다.
            //이럴 경우, 다음에 실행될 필터에서 처리해주면 된다.
            log.info("token null");
            //다음 필터로 넘김
            filterChain.doFilter(request,response);
            return;
        }
        log.info("Authorization {}",authorization);
        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)){
            log.info("token expired");
            filterChain.doFilter(request,response);
            return;
        }

        String username = jwtUtil.getUserName(token);
        String role = jwtUtil.getRole(token);

        UserDTO userDTO = new UserDTO();
//        userDTO.setId("tempId");
//        userDTO.setPassword("tempPwd");
        userDTO.setRole(role);
        userDTO.setUsername(username);
        CustomUserDetails customUserDetails = new CustomUserDetails(userDTO);

        //스프링 시큐리티 인증토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());
        //세션에 사용자 등록
        //해당 세션은 STATELESS이기때문에 요청이 끝나면 소멸됨
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
