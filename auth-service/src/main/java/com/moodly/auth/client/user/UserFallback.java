package com.moodly.auth.client.user;

import com.moodly.auth.client.user.dto.CredentialRequest;
import com.moodly.auth.client.user.dto.CredentialVerifyResponse;
import com.moodly.auth.client.user.dto.KakaoUserCreateRequest;
import com.moodly.auth.client.user.dto.KakaoUserResponse;
import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFallback implements UserServiceClient{

    @Override
    public CredentialVerifyResponse verify(CredentialRequest request) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "user-service unavailable: verify");
    }

    @Override
    public List<String> getRoles(Long userId) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "user-service unavailable: getRoles");
    }

    @Override
    public KakaoUserResponse findOrCreateKakaoUser(KakaoUserCreateRequest request) {
        throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "user-service unavailable: kakao user");
    }
}
