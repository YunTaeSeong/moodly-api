package com.moodly.common.exception;

public enum GlobalErrorCode implements ErrorCode {
    USER_NOT_FOUND("USER_001", "USER_NOT_FOUND"),

    INVALID_JWT_SECRET("JWT_001", "JWT_SECRET_NULL_OR_BLANK"),
    INVALID_JWT_TOKEN("JWT_002", "INVALID_JWT_TOKEN"),
    INVALID_REFRESH_TOKEN("JWT_003", "INVALID_REFRESH_TOKEN"),
    EXPIRED_REFRESH_TOKEN("JWT_004", "EXPIRED_REFRESH_TOKEN"),

    INTERNAL_SERVER_ERROR("501", "INTERNAL_SERVER_ERROR");


    private final String code;
    private final String message;

    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return "";
    }

    @Override
    public String message() {
        return "";
    }
}
