package com.moodly.user.response;

import java.util.List;

public record CredentialVerifyResponse(Long userId, List<String> roles) {
}
