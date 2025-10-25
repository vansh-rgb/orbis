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
    public void loggerHandlerAfter(JoinPoint joinPoint) {
        logger.info("Logger - Entering Handler Method: {}", joinPoint.getSignature().getName());
    }

    @Before(value = "execution(* com.strink.orbis.handler..*(..))")
    public void loggerHandlerBefore(JoinPoint joinPoint) {
        logger.info("Logger - Exiting Handler Method: {}", joinPoint.getSignature().getName());
    }

    @After(value = "execution(* com.strink.orbis.service..*(..))")
    public void loggerServiceAfter(JoinPoint joinPoint) {
        logger.info("Logger - Entering Service Method: {}", joinPoint.getSignature().getName());
    }

    @After(value = "execution(* com.strink.orbis.service..*(..))")
    public void loggerServiceBefore(JoinPoint joinPoint) {
        logger.info("Logger - Exiting Service Method: {}", joinPoint.getSignature().getName());
    }

}
