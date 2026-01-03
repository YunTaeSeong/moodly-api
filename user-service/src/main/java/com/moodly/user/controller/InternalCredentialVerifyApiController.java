package com.moodly.user.controller;

import com.moodly.user.dto.CredentialVerifyDto;
import com.moodly.user.request.CredentialRequest;
import com.moodly.user.response.CredentialVerifyResponse;
import com.moodly.user.service.InternalCredentialVerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/user/credential")
public class InternalCredentialVerifyApiController {

    private final InternalCredentialVerifyService verifyService;

    @PostMapping("/verify")
    public CredentialVerifyResponse verify(
            @Valid
            @RequestBody CredentialRequest request
    ) {
        CredentialVerifyDto verify = verifyService.verify(request.getEmail(), request.getPassword());
        return new CredentialVerifyResponse(verify.getUserId(), verify.getRoles());
    }
}
