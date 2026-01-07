package com.moodly.user.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordConfirmRequest(
        @NotBlank
        String token,

        @NotBlank
        String newPassword,

        @NotBlank
        String rePassword
) {
}
