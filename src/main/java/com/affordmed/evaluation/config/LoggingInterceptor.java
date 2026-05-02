package com.affordmed.evaluation.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
        ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;

        String requestBody = getBody(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());
        String responseBody = getBody(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());

        log.info("Request: {} {} | Body: {}", request.getMethod(), request.getRequestURI(), requestBody);
        log.info("Response Status: {} | Body: {}", response.getStatus(), responseBody);

        responseWrapper.copyBodyToResponse();
    }

    private String getBody(byte[] content, String encoding) throws UnsupportedEncodingException {
        if (content == null || content.length == 0) return "[empty]";
        return new String(content, encoding);
    }
}
