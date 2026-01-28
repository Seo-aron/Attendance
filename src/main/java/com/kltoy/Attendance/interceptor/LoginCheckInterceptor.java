package com.kltoy.Attendance.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        if (session == null || session.getAttribute("loginSession") == null) {
            // 로그인 되어있지 않은 경우
            System.out.println("미인증 사용자 요청: " + requestURI);
            // 로그인 페이지로 리다이렉트
            response.sendRedirect("/login/login?redirectURL=" + requestURI);
            return false; // 컨트롤러 실행 중단
        }

        // 로그인 되어있는 경우
        return true; // 컨트롤러 계속 실행
    }
}
