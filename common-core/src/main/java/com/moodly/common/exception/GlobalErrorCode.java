package com.moodly.common.exception;

public enum GlobalErrorCode {
    USER_NOT_FOUND("USER_001", "USER_NOT_FOUND");

    private final String code;
    private final String message;

    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
