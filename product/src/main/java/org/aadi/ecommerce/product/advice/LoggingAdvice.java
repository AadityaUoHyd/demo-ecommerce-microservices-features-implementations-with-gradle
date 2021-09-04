package org.aadi.ecommerce.product.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {

    @Pointcut(value="execution(* org.aadi.ecommerce.product.*.*.*(..))")
    public void myPointcut(){

    }

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();

        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();

        log.info("method invoked " + className +" : " + methodName + "()" + "arguments : " + mapper.writeValueAsString(array));

        long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();

        log.info(className + " : " + methodName + "()" +"Response : " + mapper.writeValueAsString(obj));
        log.info("Method name "+pjp.getSignature()+" time taken to execute : "+(endTime-startTime));

        return obj;
    }

   /* @Around("@annotation(org.aadi.ecommerce.product.advice.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj1=pjp.proceed();
        long endTime = System.currentTimeMillis( );
        log.info("Method name "+pjp.getSignature()+" time taken to execute : "+(endTime-startTime));
        return obj1;
    } */

}
