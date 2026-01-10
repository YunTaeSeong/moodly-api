package com.moodly.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.common.security.jwt.JwtTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * public endpoint (로그인, 회원가입 등) 예외 처리
 * 나머지는 JWT 필수
 */
@Configuration
@EnableConfigurationProperties(GatewayJwtProperties.class)
public class GatewaySecurityConfig {

    @Bean
    public JwtAuthenticationGlobalFilter jwtAuthenticationGlobalFilter(
            GatewayJwtProperties props,
            JwtVerifier jwtVerifier,
            JwtTokenProvider jwtTokenProvider,
            ObjectMapper objectMapper
    ) {
        return new JwtAuthenticationGlobalFilter(props, jwtVerifier, jwtTokenProvider, objectMapper);
    }
}
