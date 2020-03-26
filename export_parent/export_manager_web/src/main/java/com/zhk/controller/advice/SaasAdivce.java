package com.zhk.controller.advice;

import com.zhk.domain.system.SysLog;
import com.zhk.domain.system.User;
import com.zhk.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
public class SaasAdivce {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;


    @Around("execution(* com.zhk.controller.*.*.*(..))")
    public Object advice(ProceedingJoinPoint pjp) throws Throwable {
        SysLog log = new SysLog();

        User user = (User) session.getAttribute("loginUser");

        if (user != null) {
            log.setCompanyName(user.getCompanyName());
            log.setCompanyId(user.getCompanyId());
            log.setUserName(user.getUserName());
        }

        log.setId(UUID.randomUUID().toString());

        log.setIp(request.getRemoteAddr());

        log.setTime(new Date());

        Signature signature = pjp.getSignature();

        MethodSignature methodSignature = (MethodSignature) signature;

        String name = methodSignature.getMethod().getName();

        log.setMethod(name);

        if (methodSignature.getMethod().isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = methodSignature.getMethod().getAnnotation(RequestMapping.class);
            log.setAction(requestMapping.name());
        }

        sysLogService.save(log);

        return pjp.proceed();
    }
}
