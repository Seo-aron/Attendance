package com.kltoy.Attendance.config;

import com.kltoy.Attendance.chat.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
// 웹소켓 기능 On
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // "/ws/chat" 이라는 주소로 세션 연결하러 오면, chatHandler가 맞이하도록 설정
        registry.addHandler(chatHandler, "/ws/chat")
                .setAllowedOrigins("*");    // 개인프로젝트라 모든 도메인 허용
    }
}
