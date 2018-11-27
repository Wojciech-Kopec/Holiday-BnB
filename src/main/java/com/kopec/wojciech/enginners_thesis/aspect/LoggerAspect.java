package com.kopec.wojciech.enginners_thesis.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()")
    public void logInfoBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.debug("Log @Before: {} with args: {}",
                joinPoint.getSignature(), Arrays.toString(args));
    }

    @After("com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()")
    public void logInfoAfter(JoinPoint joinPoint) {
        logger.debug("Log @After: Method {} executed", joinPoint.getSignature());
    }

    @AfterThrowing(
            pointcut = "com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()",
            throwing = "error")
    public void logError(JoinPoint joinPoint, Throwable error) {
        logger.error("Log @AfterThrowing, Error: Method {} finished with error {",
                joinPoint.getSignature(), error.getMessage());
    }

    @AfterReturning(
            pointcut = "com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()",
            returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (result != null)
            logger.debug("Log @AfterReturning: Method " + joinPoint.getSignature() + " successfully returned value {} for args {}",
                    result, Arrays.toString(args));
    }
}
