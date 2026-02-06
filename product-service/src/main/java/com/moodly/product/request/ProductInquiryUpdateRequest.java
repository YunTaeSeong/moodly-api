package com.moodly.product.request;

import jakarta.validation.constraints.NotBlank;

public record ProductInquiryUpdateRequest(@NotBlank String content) {
}
