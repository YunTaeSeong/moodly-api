package com.moodly.common.exception;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "USER_NOT_FOUND"),

    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "EMAIL_001", "DUPLICATED_EMAIL"),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "EMAIL_001", "EMAIL_NOT_FOUND"),

    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "PASSWORD_001", "PASSWORD_MISMATCH"),

    INVALID_JWT_SECRET(HttpStatus.INTERNAL_SERVER_ERROR, "JWT_001", "JWT_SECRET_NULL_OR_BLANK"),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_002", "INVALID_JWT_TOKEN"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_003", "INVALID_REFRESH_TOKEN"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "JWT_004", "EXPIRED_REFRESH_TOKEN"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_001", "INTERNAL_SERVER_ERROR"),

    VERIFICATION_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH_001", "SERVER_ERROR_AUTH_EXPIRED"),
    VERIFICATION_CODE_INVALID(HttpStatus.BAD_REQUEST, "AUTH_002", "SERVER_ERROR_AUTH_INVALID"),

    MISSING_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "AUTHORIZATION_001", "MISSING_AUTHORIZATION"),

    PRODUCTID_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_001", "INVALID_PRODUCT_ID"),
    PRODUCTID_INQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_INQUIRY_001", "INVALID_PRODUCT_ID"),
    INQUIRY_ALREADY_REPLIED(HttpStatus.CONFLICT, "INQUIRY_001", "INQUIRY_ALREADY_REPLIED"),

    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_ITEM_001", "CART_ITEM_NOT_FOUND"),

    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_001", "CART_NOT_FOUND"),

    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "QUANTITY_001", "INVALID_QUANTITY"),

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_001", "ORDER_NOT_FOUND"),

    WISHLIST_ALREADY_EXISTS(HttpStatus.CONFLICT, "WISHLIST_001", "WISHLIST_ALREADY_EXISTS"),
    WISHLIST_NOT_EXISTS(HttpStatus.CONFLICT, "WISHLIST_002", "WISHLIST_NOT_EXISTS"),

    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION_001", "NOTIFICATION_NOT_FOUND"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    GlobalErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }
}
