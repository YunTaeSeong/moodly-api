package com.moodly.notification.config;

import com.moodly.notification.event.InquiryCreatedEventPayload;
import com.moodly.notification.event.InquiryRepliedEventPayload;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, InquiryCreatedEventPayload> createdConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.moodly.notification.event,com.moodly.product.event");
        // Kafka 연결 실패 시 재시도 제한
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, 10000);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(InquiryCreatedEventPayload.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InquiryCreatedEventPayload> createdListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InquiryCreatedEventPayload> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createdConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, InquiryRepliedEventPayload> repliedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.moodly.notification.event,com.moodly.product.event");
        // Kafka 연결 실패 시 재시도 제한
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, 10000);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(InquiryRepliedEventPayload.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InquiryRepliedEventPayload> repliedListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InquiryRepliedEventPayload> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(repliedConsumerFactory());
        return factory;
    }
}
