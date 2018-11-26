package com.kopec.wojciech.enginners_thesis.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    @Before("com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()")
    public void logInfoBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.printf("Log @Before: %s with args: %s\n",
                joinPoint.getSignature(), Arrays.toString(args));
    }

    @After("com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()")
    public void logInfoAfter(JoinPoint joinPoint) {
        System.out.printf("Log @After: Method %s executed \n", joinPoint.getSignature());
    }

    @AfterThrowing(
            pointcut = "com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()",
            throwing = "error")
    public void logError(JoinPoint joinPoint, Throwable error) {
        System.out.printf("Log @AfterThrowing, Error: Method %s finished with error %s\n",
                joinPoint.getSignature(), error.getMessage());
    }

    @AfterReturning(
            pointcut = "com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()",
            returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (result != null)
            System.out.printf("Log @AfterReturning: Method " + joinPoint.getSignature() + " successfully returned value %s for args %s\n",
                    result, Arrays.toString(args));
    }
}
