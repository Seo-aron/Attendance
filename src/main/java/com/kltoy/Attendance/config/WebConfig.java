package com.kltoy.Attendance.config;

import com.kltoy.Attendance.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 인터셉터 체인 순서 (1번이 가장 먼저 실행)
                .addPathPatterns("/**") // 모든 경로에 대해 인터셉터 적용
                .excludePathPatterns( // 아래 경로들은 인터셉터 적용 제외 (로그인 안해도 접근 가능)
                        "/", "/login/login", "/login/join", "/login/logout",
                        "/css/**", "/js/**", "/*.ico", "/error"
                );
    }
}
