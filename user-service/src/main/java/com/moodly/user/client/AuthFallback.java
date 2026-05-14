package com.moodly.user.client;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthFallback implements AuthClient{

    @Override
    public ResponseEntity<Void> revokeAllByUserId(String authorization, Long userId) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "auth-service unavailable: revokeAllByUserId");
    }
}
