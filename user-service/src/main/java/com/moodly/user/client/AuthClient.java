package com.moodly.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(
        name = "auth-service",
        url = "${auth-service.base-url}"
)
public interface AuthClient {

    @DeleteMapping("/auth/refresh-token/{userId}")
    ResponseEntity<Void> revokeAllByUserId(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long userId
    );
}
