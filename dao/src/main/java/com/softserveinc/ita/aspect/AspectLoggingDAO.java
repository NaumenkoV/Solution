package com.softserveinc.ita.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Aspect
public class AspectLoggingDAO {

    private final Logger logger = Logger.getLogger(getClass());

    @Around("execution(* com.softserveinc.ita.dao..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder logMessage = new StringBuilder();
        long start = System.currentTimeMillis();
        String clazz = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logMessage.append("*******************************************START*******************************************\n");
        logMessage.append("Current Date:\n");
        logMessage.append("Entering to Class ");
        logMessage.append(clazz);
        logMessage.append(" With Method Name ");
        logMessage.append(methodName);
        logMessage.append("\n");
        logMessage.append("Parameters: ");
        logMessage.append(Arrays.toString(joinPoint.getArgs()));
        logMessage.append("\n");

        Object obj = joinPoint.proceed();

        logMessage.append("Method execution completed!!!\n");
        long elapsedTime = System.currentTimeMillis() - start;
        logMessage.append("Method execution for method: ");
        logMessage.append(methodName);
        logMessage.append(" in Class ");
        logMessage.append(clazz);
        logMessage.append(" milliseconds: ");
        logMessage.append(elapsedTime);
        logMessage.append("\n");
        logMessage.append("*******************************************END*******************************************");
        logger.info(logMessage.toString());
        return obj;
    }

    @AfterThrowing(value = "execution(* com.softserveinc.ita.dao..*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String clazz = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("After Throwing From Method ");
        logMessage.append(methodName);
        logMessage.append(" In Class ");
        logMessage.append(clazz);
        logger.info(logMessage);
        logger.error(logMessage, ex);
    }
}
