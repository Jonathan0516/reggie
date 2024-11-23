package com.itheima.reggie.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: reggie_take_out
 * @description: *
 * @return:
 **/

@Component
@Slf4j
public class LoginFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // 不创建新的 Session
        log.info("Intercepted request: {}, Session ID: {}", request.getRequestURI(), session != null ? session.getId() : "null");
        log.info("Session employee: {}", session != null ? session.getAttribute("employee") : "null");

        if (session != null && session.getAttribute("employee") != null) {
            // 用户已登录，放行
            return true;
        }

        // 用户未登录，重定向到正确的登录页面路径
        response.sendRedirect(request.getContextPath() + "/backend/page/login/login.html");
        return false;
    }



}
