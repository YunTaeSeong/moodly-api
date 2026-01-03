package com.moodly.auth.request;

public record LoginRequest(
        String email,
        String password
) {
}
