package com.zhk.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaaSException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();

        if (ex instanceof UnauthorizedException) {
            mv.setViewName("redirect:/unauthorized.jsp");
        }else {
            mv.setViewName("error");

            mv.addObject("errorMsg",ex.getMessage());
        }

        return mv;
    }
}
