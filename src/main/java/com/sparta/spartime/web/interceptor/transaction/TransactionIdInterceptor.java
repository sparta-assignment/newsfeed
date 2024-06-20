package com.sparta.spartime.web.interceptor.transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class TransactionIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String trId = TransactionIdHolder.generateTransactionId();
        TransactionIdHolder.setTrId(trId);
        request.setAttribute("transactionId", trId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TransactionIdHolder.clear();
    }
}
