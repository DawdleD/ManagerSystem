package com.example.managersystem.advice;

import com.example.managersystem.dto.CommonResponse;
import com.example.managersystem.enums.ResponseCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * {@code @className:}      GlobalExceptionHandler
 * {@code @author:}     dengxiangtian
 * {@code @description:}  TODO
 * {@code @date:}    2024/4/27 9:18 AM
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResponse<String> handleSecurityException(SecurityException e) {
        return CommonResponse.buildFailResp(ResponseCodeEnum.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> handleException(Exception e) {
        return CommonResponse.buildFailResp(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
    }
}