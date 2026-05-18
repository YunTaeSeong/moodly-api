package com.moodly.product.request;

import jakarta.validation.constraints.NotBlank;

public record ProductReviewReplyRequest(
        @NotBlank String reply
) {
}
