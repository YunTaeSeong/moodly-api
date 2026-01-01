package com.moodly.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.application.name:unknown-service}")
    private String serviceName;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(
            BaseException e,
            HttpServletRequest request
    ) {
        GlobalErrorCode code = (GlobalErrorCode) e.getErrorCode();

        ErrorResponse response = new ErrorResponse(
                OffsetDateTime.now().toString(),
                request.getRequestURI(),
                null,
                serviceName,
                new ErrorResponse.ErrorBody(
                        code.code(),
                        code.message(),
                        e.getDetail(),
                        null
                )
        );

        return ResponseEntity
                .status(code.httpStatus())
                .body(response);
    }
}
