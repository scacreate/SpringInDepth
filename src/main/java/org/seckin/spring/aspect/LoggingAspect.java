package org.seckin.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);


    @Pointcut("@annotation(Loggable)")
    public void executeLogging(){

    }

    @Before("executeLogging()")
    public void logMethodCall(JoinPoint joinPoint){
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();

        if(null!=args && args.length > 0){
           message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg ->{
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        LOGGER.info(message.toString());
    }

   /* @AfterReturning(value = "executeLogging()", returning = "returnValue")
    public void logMethodCall(JoinPoint joinPoint, Object returnValue){
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();

        if(null!=args && args.length > 0){
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg ->{
                message.append(arg).append(" | ");
            });
            message.append("]");
        }

        if(returnValue instanceof Collection){
            message.append(", returning: ").append(((Collection) returnValue).size()).append(" instance(s)");
        }else{
            message.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message.toString());
    }*/

    @Around(value = "executeLogging()")
    public String logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis()-startTime;
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        message.append(" totalTime: ").append(totalTime).append("ms");
        Object[] args = joinPoint.getArgs();

        if(null!=args && args.length > 0){
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg ->{
                message.append(arg).append(" | ");
            });
            message.append("]");
        }

        if(returnValue instanceof Collection){
            message.append(", returning: ").append(((Collection) returnValue).size()).append(" instance(s)");
        }else{
            message.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message.toString());

        return returnValue.toString();
    }


    @Pointcut("@annotation(Countable)")
    public void executeCount(){

    }

    @After("executeCount()")
    public void countMethodCall(JoinPoint joinPoint){
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();

        final Integer[] callCount = new Integer[1];

        if(null!=args && args.length > 0){
            message.append(" statistics=[ totalMethodCall: ");
            Arrays.asList(args).forEach(arg ->{
                if(arg instanceof Integer)
                    message.append((Integer)arg+1);
            });
            message.append("]");
        }
        LOGGER.info(message.toString());
    }


}
