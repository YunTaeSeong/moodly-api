package com.moodly.common.exception;

public record ErrorResponse(
        String timestamp,
        String path,
        String traceId,
        String service,
        ErrorBody body
) {
    public record ErrorBody(
            String code,
            String message,
            String detail,
            FieldErrors fieldErrors
    ) {}

    public record FieldErrors(
            String field,
            String rejectedValue,
            String reason
    ) {}
}
