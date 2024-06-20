package com.sparta.spartime.web.exception.handler;

import com.sparta.spartime.dto.response.EnvelopeResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "UNHANDLED EXCEPTION")
@Order(100)
@RestControllerAdvice
public class UnHandledExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleUnhandledException(Exception e, HttpServletRequest request) {
        log.info("", e);
        return EnvelopeResponse.wrapError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                request.getRequestURI()
        );
    }
}
