package com.mdv.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mdv.identity_service.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        log.error("Internal server error", e);

        ApiErrorCode apiErrorCode = ApiErrorCode.UNCATEGORIZED_EXCEPTION;
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(apiErrorCode.getHttpCode())
                .message(apiErrorCode.getErrorMessage())
                .build();

        return ResponseEntity.status(apiErrorCode.getHttpCode()).body(response);
    }

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException e) {
        log.error("API error", e);

        ApiErrorCode apiException = e.getApiErrorCode();
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(apiException.getHttpCode())
                .message(apiException.getErrorMessage())
                .build();

        return ResponseEntity.status(apiException.getHttpStatus()).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Invalid request", e);

        ApiErrorCode apiErrorCode = ApiErrorCode.INVALID_KEY;

        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = (fieldError != null) ? fieldError.getDefaultMessage() : apiErrorCode.getErrorMessage();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(apiErrorCode.getHttpCode())
                .message(message)
                .build();

        return ResponseEntity.status(apiErrorCode.getHttpCode()).body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access denied", e);

        ApiErrorCode apiErrorCode = ApiErrorCode.UNAUTHORIZED;
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(apiErrorCode.getHttpCode())
                .message(apiErrorCode.getErrorMessage())
                .build();

        return ResponseEntity.status(apiErrorCode.getHttpCode()).body(response);
    }

    @ExceptionHandler(value = JWTSigningException.class)
    public ResponseEntity<ApiResponse<Void>> handleJWTSigningException(JWTSigningException e) {
        log.error("JWT signing error", e);

        ApiErrorCode apiErrorCode = ApiErrorCode.JWT_SIGNING_ERROR;
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(apiErrorCode.getHttpCode())
                .message(apiErrorCode.getErrorMessage())
                .build();

        return ResponseEntity.status(apiErrorCode.getHttpCode()).body(response);
    }
}
