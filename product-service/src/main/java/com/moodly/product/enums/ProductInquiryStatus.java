package com.moodly.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductInquiryStatus {
    PENDING("답변대기"),
    COMPLETED("답변완료");

    private String description;
}
