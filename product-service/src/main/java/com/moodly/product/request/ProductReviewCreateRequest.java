package com.moodly.product.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProductReviewCreateRequest(
        @NotNull Long orderItemId,
        @NotNull Long productId,
        @NotNull @Min(1) @Max(5) Integer rating,
        @NotBlank String content,
        @Size(max = 3) List<String> images
) {
}
