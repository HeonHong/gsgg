package com.gsgg.gsggbe.test.webSocketTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    //
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //destination header 설정
        config.enableSimpleBroker("/send","/live");
    }

    @Override
    //endpoint 정의
    //핸드쉐이크 CORS 해제
    //웹소켓 핸드셰이크 커넥션 생성할 경로 지정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
