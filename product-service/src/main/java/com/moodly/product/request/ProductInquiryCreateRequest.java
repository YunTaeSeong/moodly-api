package com.moodly.product.request;

import jakarta.validation.constraints.NotBlank;

public record ProductInquiryCreateRequest(@NotBlank String content) {
}
