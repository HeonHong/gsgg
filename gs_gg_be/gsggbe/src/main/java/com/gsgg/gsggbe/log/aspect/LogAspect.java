package com.gsgg.gsggbe.log.aspect;

import com.gsgg.gsggbe.log.entity.LogEntity;
import com.gsgg.gsggbe.mapper.log.LogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class LogAspect {
    private final LogMapper logMapper;

    @AfterReturning("execution(* com.gsgg.gsggbe..*(..))")
    public void logExecution(JoinPoint joinPoint) {
        LogEntity logEntity = new LogEntity();

//        List<Map<String, Object>> result = logMapper.insertLogTest(logEntity);

//        log.info("result==========={}", result);
        log.info("jointPoint============{}", joinPoint.getSignature().getName());
    }
}
