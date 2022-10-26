package com.example.capston.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {


    @Before("@annotation(com.example.capston.aspect.Log)")
    public void doLog(JoinPoint joinPoint){
        log.info("[target] = {} [args] = {} ", joinPoint.getSignature(), joinPoint.getArgs());
    }
}
