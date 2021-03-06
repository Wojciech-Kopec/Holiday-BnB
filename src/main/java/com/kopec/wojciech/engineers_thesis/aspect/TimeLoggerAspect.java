package com.kopec.wojciech.engineers_thesis.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeLoggerAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeLoggerAspect.class);

/* TODO Uncomment when implementation will be complete - as for now it is only polluting logs.
    @Around("com.kopec.wojciech.engineers_thesis.aspect.AspectUtil.allMethods()")
    public Object measureExecTime(ProceedingJoinPoint pjp) throws Throwable {
        Instant before = Instant.now();
        // @Before
        try {
            return pjp.proceed();
            //}catch (Throwable e) {
            //  @AfterThrowing, if throws was not present
            //}
        } finally {
            // @After
            // @AfterReturning
            Instant after = Instant.now();
            Duration execTime = Duration.between(before, after);
            logger.debug("{} execution took {} ms", pjp.toShortString(), execTime.toMillis());
        }
    }*/
}
