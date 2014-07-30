package com.softserveinc.ita.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
public class AopControllerLogging {
    private final Logger logger = Logger.getLogger(getClass());
    private MethodSignature signature = null;
    private String[] requestMappingValues = null;
    private StringBuilder logMessage = null;
    private String controllerURL = "";
    private String requestMappingMethod = "";
    private String methodURL = "";
    private Object[] realParameters;

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void beanAnnotatedWithController() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void methodWithAnnotationRequestMapping() {
    }

    @Pointcut("publicMethod() && methodWithAnnotationRequestMapping() && beanAnnotatedWithController()")
    public void publicMethodWithAnnotationRequestMappingInsideAClassMarkedWithController() {
    }

    @Around("publicMethodWithAnnotationRequestMappingInsideAClassMarkedWithController()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        controllerURL = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class).value()[0];
        signature = (MethodSignature) joinPoint.getSignature();
        requestMappingValues = signature.getMethod().getAnnotation(RequestMapping.class).value();
        logMessage = new StringBuilder();
        realParameters = joinPoint.getArgs();
        requestMappingMethod = signature.getMethod().getAnnotation(RequestMapping.class).method()[0].toString();
        if (requestMappingValues.length != 0) {
            methodURL = requestMappingValues[0];
        }
        logMessage.append(" ********START SNIPPET********\n");
        logMessage.append(requestMappingMethod + " request to the URL: " + controllerURL + methodURL + "\n");
        logMessage.append(" from class: " + joinPoint.getTarget().getClass().getName()+"\n");
        if (realParameters.length!=0) {
            logMessage.append(" Passed parameter is (are): ");
            for (Object o : realParameters) {
                if(o != null) {
                    logMessage.append(o.toString() + " \n");
                }
            }
        } else {
            logMessage.append(" no parameters have been passed\n");
        }
        Object obj = joinPoint.proceed();
        logMessage.append(" Returned object is: "+obj.toString()+"\n");
        logMessage.append(" ********END SNIPPET********");
        logger.info(logMessage.toString());
        System.out.println(logMessage.toString());
        return obj;
    }

    @AfterThrowing(pointcut = "publicMethodWithAnnotationRequestMappingInsideAClassMarkedWithController()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String clazz = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("After Throwing From Method ");
        logMessage.append(methodName);
        logMessage.append(" In Class ");
        logMessage.append(clazz);
        logger.error(logMessage);
    }
}