package com.kopec.wojciech.enginners_thesis.aspect;

import java.time.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeLoggerAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeLoggerAspect.class);


    @Around("com.kopec.wojciech.enginners_thesis.aspect.AspectUtil.allMethods()")
    public Object measureExecTime(ProceedingJoinPoint pjp) throws Throwable {
        Instant before = Instant.now();
        // @Before
        try {
            return pjp.proceed();
            //}catch (Throwable e) {
            //  @AfterThrowing, gdyby nie było throws
            //}
        } finally {
            // @After
            // @AfterReturning
            Instant after = Instant.now();
            Duration execTime = Duration.between(before, after);
            logger.debug("{} execution took {} ms", pjp.toShortString(), execTime.toMillis());
        }
    }
}
