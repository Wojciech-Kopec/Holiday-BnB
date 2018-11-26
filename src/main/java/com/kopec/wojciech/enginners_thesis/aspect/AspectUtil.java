package com.kopec.wojciech.enginners_thesis.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectUtil {
    @Pointcut("execution(* com.kopec.wojciech.enginners_thesis..*.*(..))")
    public void allMethods() {
    }
}
