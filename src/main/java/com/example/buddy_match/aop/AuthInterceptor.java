package com.example.buddy_match.aop;

import com.example.buddy_match.anotation.WithAuth;
import com.example.buddy_match.enums.UserRoleEnum;
import com.example.buddy_match.exception.BusinessException;
import com.example.buddy_match.model.atest.Team;
import com.example.buddy_match.service.TeamServiceImpl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@Aspect
@Component
public class AuthInterceptor {
    /** This is an example service. */

    /**
     * UseCase:
     * 
     * @WithAuth(mustRole = UserRoleEnum.USER)
     *                    public String getMethodName(@RequestParam String param) {
     *                    return new String();
     *                    }
     */
    @Around("@annotation(withAuth)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, WithAuth withAuth) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // if (userService.getCurrent(request) == null)
        // throw new BadRequestException("User session not found");
        UserRoleEnum mustRoleEnum = withAuth.mustRole();

        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }

        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        // userRoleEnum = users.getUserRole();
        if (userRoleEnum == null) {
            throw new BusinessException("Permission denied");
        }
        if (UserRoleEnum.BAN.equals(userRoleEnum)) {
            throw new BusinessException("Permission denied");
        }
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)) {
            if (!UserRoleEnum.ADMIN.equals(userRoleEnum)) {
                throw new BusinessException("Permission denied");
            }
        }

        return joinPoint.proceed();
    }
}