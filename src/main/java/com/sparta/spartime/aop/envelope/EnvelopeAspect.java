package com.sparta.spartime.aop.envelope;

import com.sparta.spartime.dto.response.EnvelopeResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class EnvelopeAspect {
    @Around("@annotation(com.sparta.spartime.aop.envelope.Envelope)")
    public Object envelopeResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Envelope envelopeAnnotation = method.getAnnotation(Envelope.class);
        String msg = envelopeAnnotation.value().isBlank() ? method.getName() + " ok" : envelopeAnnotation.value();
        Object result = joinPoint.proceed();

        if (!(result instanceof ResponseEntity<?> responseEntity)) {
            return result;
        }
        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .body(
                        new EnvelopeResponse<>(
                                responseEntity.getBody(),
                                responseEntity.getStatusCode().value(),
                                msg
                        )
                );

    }
}
