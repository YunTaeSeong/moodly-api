package com.moodly.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
