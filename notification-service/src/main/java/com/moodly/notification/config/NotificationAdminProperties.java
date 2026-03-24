package com.moodly.notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * POC: 알림 수신 관리자 user_id 목록 (실서비스에서는 user-service에서 role=ADMIN 조회)
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "notification")
public class NotificationAdminProperties {
    /** 관리자 user_id 목록 (문의 등록 시 이들에게 알림) */
    private List<Long> adminUserIds = Collections.singletonList(1L);
}
