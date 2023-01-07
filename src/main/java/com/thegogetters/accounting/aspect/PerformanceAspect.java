package com.thegogetters.accounting.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Pointcut("@annotation(com.thegogetters.accounting.annotation.ExecutionTime)")
    public void executionTimePC() {}

    @Around("executionTimePC()")
    public Object aroundAnyExecutionTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) {

        long beforeTime = System.currentTimeMillis();
        Object result = null;

        try{
            result = proceedingJoinPoint.proceed();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }

        long afterTime = System.currentTimeMillis();

        log.info("[Performance Log]: Execution Time-> {} ms, User-> {}, Operation-> {}"
                , (afterTime - beforeTime), getUsername(), proceedingJoinPoint.getSignature().toShortString());

        return result;
    }
}