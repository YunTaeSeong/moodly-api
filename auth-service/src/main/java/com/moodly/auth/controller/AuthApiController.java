package com.moodly.auth.controller;

import com.moodly.auth.client.user.UserServiceClient;
import com.moodly.auth.client.user.dto.CredentialRequest;
import com.moodly.auth.client.user.dto.CredentialVerifyResponse;
import com.moodly.auth.request.LoginRequest;
import com.moodly.auth.request.RefreshRequest;
import com.moodly.auth.response.TokenPairResponse;
import com.moodly.auth.token.JwtTokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {
    private final UserServiceClient userServiceClient;
    private final JwtTokenIssuer tokenIssuer;

    @PostMapping("/login")
    public TokenPairResponse login(@RequestBody LoginRequest req) {
        CredentialVerifyResponse verify = userServiceClient.verify(new CredentialRequest(req.email(), req.password()));
        return tokenIssuer.issue(verify.getUserId(), verify.getRoles());
    }

    @PostMapping("/refresh")
    public TokenPairResponse refresh(@RequestBody RefreshRequest req) {
        return tokenIssuer.refresh(req.refreshToken());
    }
}
