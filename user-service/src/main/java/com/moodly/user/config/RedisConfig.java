package com.moodly.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.user.verification.FindIdVerificationValue;
import com.moodly.user.verification.PasswordResetValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean(name = "findIdRedisTemplate")
    public RedisTemplate<String, FindIdVerificationValue> findIdRedisTemplate(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper
    ) {
        RedisTemplate<String, FindIdVerificationValue> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<FindIdVerificationValue> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, FindIdVerificationValue.class);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "passwordResetRedisTemplate")
    public RedisTemplate<String, PasswordResetValue> passwordResetRedisTemplate(
            RedisConnectionFactory connectionFactory,
            ObjectMapper objectMapper)
    {
        RedisTemplate<String, PasswordResetValue> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<PasswordResetValue> serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, PasswordResetValue.class);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
