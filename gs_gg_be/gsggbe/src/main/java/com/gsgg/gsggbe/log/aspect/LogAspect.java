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
    ExecutorService executorService;
    @Autowired
    JWTUtil jwtUtil;

    String token = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return true; // 토큰이 유효하면 요청을 계속 진행
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false; // 토큰이 없거나 유효하지 않으면 요청 중단

        //return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @AfterReturning("execution(* com.gsgg.gsggbe.**.*service..*(..)) && !execution(* *..log..*(..))")
    public void logExecution(JoinPoint joinPoint) {
        executorService.submit(() -> {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            try {
                InetAddress localHost = InetAddress.getLocalHost();
                System.out.println("local host==========" + localHost.getHostAddress());

//                String token = extractToken(request);

                System.out.println(jwtUtil.getUserName(token));

            } catch (Exception e) {
                log.error(e.getMessage());
            }

//            LogEntity logEntity = new LogEntity();

//            List<Map<String, Object>> result = logMapper.insertLogTest(logEntity);
//
//            log.info("result==========={}", result);
            log.info("jointPoint============{}", joinPoint.getSignature().getName());
        });
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
