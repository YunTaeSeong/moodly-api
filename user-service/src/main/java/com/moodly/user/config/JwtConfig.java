package com.moodly.user.config;

import com.moodly.common.security.jwt.JwtProperties;
import com.moodly.common.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider(JwtProperties properties) {
        return JwtTokenProvider.from(properties);
    }

}
