package com.kopec.wojciech.engineers_thesis.aspect;

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

//    @Before("com.kopec.wojciech.engineers_thesis.aspect.AspectUtil.allMethods()")
//    public void logInfoBefore(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        logger.debug("@Before: {} with args: {}".replace(toShorten, ""),
//                joinPoint.getSignature(), Arrays.toString(args));
//    }

//    @After("com.kopec.wojciech.engineers_thesis.aspect.AspectUtil.allMethods()")
//    public void logInfoAfter(JoinPoint joinPoint) {
//        logger.debug("@After: Method {} executed".replace(toShorten, ""), joinPoint.getSignature());
//    }

    @AfterThrowing(
            pointcut = "com.kopec.wojciech.engineers_thesis.aspect.AspectUtil.allMethods()",
            throwing = "error")
    public void logError(JoinPoint joinPoint, Throwable error) {
        logger.error(
                "@AfterThrowing, Error: Method {} with args {}\nfinished with error {}",
                joinPoint.getSignature(), joinPoint.getArgs(), error.getMessage()
        );
    }

    @AfterReturning(
            pointcut = "com.kopec.wojciech.engineers_thesis.aspect.AspectUtil.allMethods()",
            returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (result != null)
            logger.debug(
                    "@AfterReturning: Method {} with args {}\nsuccessfully returned value: {}",
                    joinPoint.getSignature(), Arrays.toString(args), result
            );
    }
}
