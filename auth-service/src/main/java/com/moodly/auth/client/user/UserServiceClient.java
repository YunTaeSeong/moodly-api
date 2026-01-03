package com.moodly.auth.client.user;

import com.moodly.auth.client.user.dto.CredentialRequest;
import com.moodly.auth.client.user.dto.CredentialVerifyResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "user-service",
        url = "${user-service.url}"
)
public interface UserServiceClient {

    @PostMapping("/internal/user/credential/verify")
    CredentialVerifyResponse verify(
            @Valid
            @RequestBody CredentialRequest request
    );

    @GetMapping("/internal/user/{userId}/roles")
    List<String> getRoles(@PathVariable("userId") Long userId);
}
