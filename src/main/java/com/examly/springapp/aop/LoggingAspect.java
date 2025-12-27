package com.examly.springapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    @Pointcut("execution(* com.examly.springapp.controller.*.*(..))")
    public void controllerMethods() {}
    
    @Pointcut("execution(* com.examly.springapp.service.*.*(..))")
    public void serviceMethods() {}
    
    @Pointcut("execution(* com.examly.springapp.repository.*.*(..))")
    public void repositoryMethods() {}
    
    @Before("controllerMethods()")
    public void logBeforeController(JoinPoint joinPoint) {
        logger.info("Entering controller method: {} with arguments: {}", 
                   joinPoint.getSignature().getName(), 
                   Arrays.toString(joinPoint.getArgs()));
    }
    
    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        logger.info("Exiting controller method: {} with result: {}", 
                   joinPoint.getSignature().getName(), 
                   result != null ? result.getClass().getSimpleName() : "null");
    }
    
    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void logControllerException(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in controller method: {} with message: {}", 
                    joinPoint.getSignature().getName(), 
                    exception.getMessage());
    }
    
    @Before("serviceMethods()")
    public void logBeforeService(JoinPoint joinPoint) {
        logger.debug("Entering service method: {} with arguments: {}", 
                    joinPoint.getSignature().getName(), 
                    Arrays.toString(joinPoint.getArgs()));
    }
    
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        logger.debug("Exiting service method: {} with result: {}", 
                    joinPoint.getSignature().getName(), 
                    result != null ? result.getClass().getSimpleName() : "null");
    }
    
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logServiceException(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in service method: {} with message: {}", 
                    joinPoint.getSignature().getName(), 
                    exception.getMessage());
    }
    
    @Around("repositoryMethods()")
    public Object logAroundRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        logger.debug("Executing repository method: {} with arguments: {}", 
                    joinPoint.getSignature().getName(), 
                    Arrays.toString(joinPoint.getArgs()));
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            
            logger.debug("Repository method: {} executed successfully in {} ms", 
                        joinPoint.getSignature().getName(), 
                        (endTime - startTime));
            
            return result;
        } catch (Exception exception) {
            long endTime = System.currentTimeMillis();
            
            logger.error("Exception in repository method: {} after {} ms with message: {}", 
                        joinPoint.getSignature().getName(), 
                        (endTime - startTime), 
                        exception.getMessage());
            
            throw exception;
        }
    }
    
    @Around("execution(* com.examly.springapp.service.*.create*(..))")
    public Object logCreateOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Creating new entity via method: {}", joinPoint.getSignature().getName());
        
        try {
            Object result = joinPoint.proceed();
            logger.info("Successfully created entity via method: {}", joinPoint.getSignature().getName());
            return result;
        } catch (Exception exception) {
            logger.error("Failed to create entity via method: {} with error: {}", 
                        joinPoint.getSignature().getName(), 
                        exception.getMessage());
            throw exception;
        }
    }
    
    @Around("execution(* com.examly.springapp.service.*.update*(..))")
    public Object logUpdateOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Updating entity via method: {}", joinPoint.getSignature().getName());
        
        try {
            Object result = joinPoint.proceed();
            logger.info("Successfully updated entity via method: {}", joinPoint.getSignature().getName());
            return result;
        } catch (Exception exception) {
            logger.error("Failed to update entity via method: {} with error: {}", 
                        joinPoint.getSignature().getName(), 
                        exception.getMessage());
            throw exception;
        }
    }
    
    @Around("execution(* com.examly.springapp.service.*.delete*(..))")
    public Object logDeleteOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Deleting entity via method: {}", joinPoint.getSignature().getName());
        
        try {
            Object result = joinPoint.proceed();
            logger.info("Successfully deleted entity via method: {}", joinPoint.getSignature().getName());
            return result;
        } catch (Exception exception) {
            logger.error("Failed to delete entity via method: {} with error: {}", 
                        joinPoint.getSignature().getName(), 
                        exception.getMessage());
            throw exception;
        }
    }
}