package com.moodly.product.request;

import jakarta.validation.constraints.NotBlank;

public record ProductInquiryReplyRequest(@NotBlank String reply) {
}
