package com.gsgg.gsggbe.log.aspect;

import com.gsgg.gsggbe.log.entity.LogEntity;
import com.gsgg.gsggbe.mapper.log.LogMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.netty.http.Cookies;

import java.net.HttpCookie;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class LogAspect implements HandlerInterceptor {
    private final LogMapper logMapper;

    private final ExecutorService executorService;

    @AfterReturning("execution(* com.gsgg.gsggbe..*(..)) && !execution(* *..log..*(..))")
    public void logExecution(JoinPoint joinPoint, HttpServletRequest request) {
        executorService.submit(() -> {
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                System.out.println("local host==========" + localHost.getHostAddress());


//                Cookie[] cookies = request.getCookies();
//
//                if(cookies != null) {
//                    for(Cookie c : cookies) {
//                        System.out.println(c.getValue());
//                    }
//                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }

            LogEntity logEntity = new LogEntity();

            List<Map<String, Object>> result = logMapper.insertLogTest(logEntity);

            log.info("result==========={}", result);
            log.info("jointPoint============{}", joinPoint.getSignature().getName());
        });
    }
}
