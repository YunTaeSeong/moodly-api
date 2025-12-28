package com.moodly.common.exception;

public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public BaseException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public BaseException(ErrorCode errorCode, String detail) {
        this(errorCode, detail, null);
    }

    public BaseException(ErrorCode errorCode, String detail, Throwable cause) {
        super(errorCode.message(), cause);
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public ErrorCode getErrorCode() { return errorCode; }
    public String getDetail() { return detail; }
}
