package com.kopec.wojciech.engineers_thesis.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectUtil {
    @Pointcut("execution(* com.kopec.wojciech.engineers_thesis..*.*(..))")
    public void allMethods() {
    }
}
