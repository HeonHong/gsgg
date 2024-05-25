package com.gsgg.gsggbe.log.aspect;

import com.gsgg.gsggbe.log.entity.LogEntity;
import com.gsgg.gsggbe.login.jwt.JWTUtil;
import com.gsgg.gsggbe.mapper.log.LogMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Aspect
@Component
//@AllArgsConstructor
@Slf4j
public class LogAspect implements HandlerInterceptor {

    @Autowired
    LogMapper logMapper;
    @Autowired
    JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            return true; // 토큰이 유효하면 요청을 계속 진행
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false; // 토큰이 없거나 유효하지 않으면 요청 중단

        //return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @AfterReturning("execution(* com.gsgg.gsggbe.**.*service..*(..)) && !execution(* *..log..*(..))  && !execution(* *..login..*(..))")
    public void logExecution(JoinPoint joinPoint) {
        try {
            // 토큰 가져오는 로직
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("Authorization").substring(7);
            String userId = jwtUtil.getUserName(token); //로그인 아이디

            // 클라이언트 IP
            String ip = InetAddress.getLocalHost().getHostAddress();

            //실행 메서드
            String methodName = joinPoint.getSignature().getName();

            if(!userId.isEmpty()) {
                LogEntity logEntity = LogEntity.builder()
                        .userId(userId)
                        .methodName(methodName)
                        .ip(ip)
                        .build();

                logMapper.insertDbLog(logEntity);
            }


            log.info("ip=========={}", userId);
            log.info("ip=========={}", ip);
            log.info("ip=========={}", methodName);
        } catch (Exception e) {
            log.error(e.getMessage());
        }



    }
}
