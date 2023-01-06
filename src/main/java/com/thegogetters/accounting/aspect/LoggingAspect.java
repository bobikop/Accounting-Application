package com.thegogetters.accounting.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? authentication.getName() : "Anonymous";
    }

    //create PointCut
    @Pointcut("execution(* com.thegogetters.accounting.controller.*.*(..)) ")
    public void anyControllerPointCut() {
    }

    @Pointcut("execution(* com.thegogetters.accounting.service.*.*(..)) && !execution(* com.thegogetters.accounting.service.SecurityServiceImpl*.*(..)))")
    public void anyServicePointCut() {
    }

    @Before("anyControllerPointCut()")
    public void beforeControllerAdvice(JoinPoint joinPoint) {
        log.info("[Controller Operation]: User -> {}, Method -> {}, Parameters -> {} "
                , getUserName()
                , joinPoint.getSignature().getName()
                , Arrays.toString(joinPoint.getArgs()));
    }

    @Before("anyServicePointCut()")
    public void beforeServiceAdvice(JoinPoint joinPoint) {
        log.info("[Service Operation]: User -> {}, Method -> {}, Parameters -> {} "
                , getUserName()
                , joinPoint.getSignature().getName()
                , Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "anyControllerPointCut()", throwing = "exception")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Exception exception) {
        log.info("After -> Method: {}, User: {}, Results: {}"
                , getUserName()
                , joinPoint.getSignature().getName()
                , exception.getMessage());
    }

    @AfterReturning(pointcut = "anyControllerPointCut()", returning = "results")
    public void afterReturningAnyControllerAdvice(JoinPoint joinPoint, Object results) {
        log.info("[Controller Operation]: User -> {}, Method -> {}, Parameters -> {} "
                , getUserName()
                , joinPoint.getSignature().toShortString()
                , results.toString());
    }

    @AfterReturning(pointcut = "anyServicePointCut()", returning = "results")
    public void afterReturningAnyServiceAdvice(JoinPoint joinPoint, Object results) {
        log.info("[Service Operation]: User -> {}, Method -> {}, Parameters -> {} "
                , getUserName()
                , joinPoint.getSignature().toShortString()
                , results.toString());
    }

    @Around("anyControllerPointCut() || anyServicePointCut()")
    public Object anyControllerPointCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long beforeOperation = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long afterOperation = System.currentTimeMillis();
        long operationTime = afterOperation - beforeOperation;
        log.info("[Performance Log]: Execution Time-> {} ms, User-> {}, Operation-> {}"
                , operationTime
                , getUserName()
                , proceedingJoinPoint.getSignature().toShortString());
        return result;
    }
}
