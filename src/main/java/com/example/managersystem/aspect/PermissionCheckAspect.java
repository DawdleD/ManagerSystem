package com.example.managersystem.aspect;

import com.example.managersystem.annotation.PermissionCheck;
import com.example.managersystem.context.UserContext;
import com.example.managersystem.enums.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class PermissionCheckAspect {

    @Around("@annotation(com.example.managersystem.annotation.PermissionCheck)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PermissionCheck permissionCheck = method.getAnnotation(PermissionCheck.class);
        UserRoleEnum[] allowedRoles = permissionCheck.value();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        UserContext userContext = (UserContext) requestAttributes.getAttribute("userContext", RequestAttributes.SCOPE_REQUEST);

        if (userContext != null && Arrays.stream(allowedRoles).anyMatch(role -> role.getDesc().equals(userContext.getRole()))) {
            log.info("User {} begin access method={}", userContext, method.getName());
            return joinPoint.proceed();
        } else {
            log.info("User {} does not have permission to access method={}", userContext, method.getName());
            throw new SecurityException("privilege not qualified");
        }
    }
}