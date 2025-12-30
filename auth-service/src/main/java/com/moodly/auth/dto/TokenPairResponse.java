package com.moodly.auth.dto;

public record TokenPairResponse(
        String accessToken,
        String refreshToken
) {}
