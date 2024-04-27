package com.example.managersystem.interceptors;

import com.example.managersystem.context.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@code @className:}      UserContextInterceptor
 * {@code @author:}     dengxiangtian
 * {@code @description:}  Interceptor used for extracting user information from base64-encoded http header info.
 * {@code @date:}    2024/4/27 1:03 AM
 */
@Component
@Slf4j
public class UserContextInterceptor implements HandlerInterceptor {

    @Value("${user.context.header}")
    String header;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userContextHeader = request.getHeader(header);
        if (userContextHeader != null) {
            try {
                userContextHeader = new String(Base64.getDecoder().decode(userContextHeader));
                ObjectMapper objectMapper = new ObjectMapper();
                UserContext userContext = objectMapper.readValue(userContextHeader, UserContext.class);
                request.setAttribute("userContext", userContext);
            } catch (Exception e) {
                log.error("UserContextInterceptor error, userContextHeader={}", userContextHeader, e);
                throw new IllegalArgumentException("User context header is invalid");
            }
        } else {
            throw new IllegalArgumentException("User context header is missing");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}