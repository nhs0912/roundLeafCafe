package com.ypdchurch.roundleafcafe.common.handler.aop;

import com.ypdchurch.roundleafcafe.common.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class CustomValidationAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
        log.info(" postMapping 시작");
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Around("postMapping() || putMapping()") //joinPoint의 전후 제어
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); // joinPoint의 매개변수
        for (Object arg : args) {
            if (arg instanceof BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

}
