package com.gmail.eugene.shchemelyov.editor.service.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class MethodTimeAspect {
    private static final Logger logger = LogManager.getLogger(MethodTimeAspect.class);
    private long startTime;

    @Pointcut("execution(* com.gmail.eugene.shchemelyov.editor.service.impl.DocumentServiceImpl.*(..))")
    public void allMethodsPointcut() {
    }

    @Before("allMethodsPointcut()")
    public void allStartServiceMethodsAdvice(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultDate = new Date(startTime);
        logger.info(String.format("%s %s start at %s",
                "Start method",
                joinPoint.getSignature().getName(),
                sdf.format(resultDate)));
    }

    @After("allMethodsPointcut()")
    public void allEndServiceMethodsAdvice(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.ms");
        Date resultDate = new Date(endTime);
        logger.info(String.format("%s %s end at %s",
                "End method",
                joinPoint.getSignature().getName(),
                sdf.format(resultDate)));
        logger.info(String.format("%s %s: %s ms",
                "Method length",
                joinPoint.getSignature().getName(),
                endTime - startTime
        ));
    }
}
