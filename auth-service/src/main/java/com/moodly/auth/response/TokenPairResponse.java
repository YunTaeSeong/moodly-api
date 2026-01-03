package com.moodly.auth.response;

public record TokenPairResponse(
        String accessToken,
        String refreshToken
) {}
