package dev.ctc.learning.rappellemoiapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Aspect
@Slf4j
@Component
public class AopControllerLogging {
    @Before("controllerMethods()")
    public void logControllerBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        Class declaringType = signature.getDeclaringType();
        log.info("==> Method {}.{}()", declaringType, name);
    }

    @AfterReturning(value = "controllerMethods()", returning = "returnValue")
    public void logControllerAfter(JoinPoint joinPoint, ResponseEntity returnValue) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        Class declaringType = signature.getDeclaringType();
        log.info("<== Method {}.{}()  {}", declaringType, name, returnValue.getStatusCode().value());
    }

    @Pointcut("execution(* dev.ctc.learning.rappellemoiapi..*Controller.*(..))")
    public void controllerMethods(){}
}
