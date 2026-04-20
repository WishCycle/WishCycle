package com.example.wishcycle.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(); // This line runs before request reaches our Controller

        if (session.getAttribute("member") == null) {
            response.sendRedirect("/login-page");
            return false; // Blocks request
        }
        return true; // Continues to controller
    }
}
