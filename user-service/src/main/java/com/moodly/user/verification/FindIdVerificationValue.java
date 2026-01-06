package com.moodly.user.verification;

public record FindIdVerificationValue(String codeHash, String maskedEmail) {
}
