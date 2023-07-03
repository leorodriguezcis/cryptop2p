package com.spring.universidad.cryptop2p.model.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {
   private final Logger logger = LoggerFactory.getLogger(AspectLogger.class);

    @Around("execution(* com.spring.universidad.cryptop2p.model.controller.*.*(..))")
    public Object logControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Execution time of method '{}' in class '{}': {} ms", methodName, className, executionTime);
        return result;
    }

}
