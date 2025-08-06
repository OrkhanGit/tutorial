package org.example.tutorial.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* org.example.tutorial.service.*.*(..))")
    public void allServiceMethods(){}

    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint){
        log.info("Method start {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "allServiceMethods()", returning = "result" )
    public void logAfterSuccess(JoinPoint joinPoint, Object result){
        log.info("Method finished {} , Result {}", joinPoint.getSignature().getName(),result);
    }

    @AfterThrowing(value = "allServiceMethods()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e){
        log.info("Method finished {} , Exception {}", joinPoint.getSignature().getName(),e);
    }

    @After("allServiceMethods()")
    public void logAfter(JoinPoint joinPoint){
        log.info("Method finished {}", joinPoint.getSignature().getName());
    }
}