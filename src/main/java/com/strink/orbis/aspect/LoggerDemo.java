package com.strink.orbis.aspect;

import org.apache.logging.slf4j.SLF4JLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerDemo {

    Logger logger = LoggerFactory.getLogger(SLF4JLogger.class);

    @After(value = "execution(* com.strink.orbis.handler..*(..))")
    public void loggerHandler(JoinPoint joinPoint) {
        logger.info("Logger - Method: {}", joinPoint.getSignature().getName());
    }

    @After(value = "execution(* com.strink.orbis.service..*(..))")
    public void loggerService(JoinPoint joinPoint) {
        logger.info("Logger - Method: {}", joinPoint.getSignature().getName());
    }

}
