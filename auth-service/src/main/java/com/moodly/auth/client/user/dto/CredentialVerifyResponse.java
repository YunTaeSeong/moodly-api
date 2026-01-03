package com.moodly.auth.client.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialVerifyResponse {

    private Long userId;
    private List<String> roles;
}
