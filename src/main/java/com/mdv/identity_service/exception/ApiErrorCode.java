package com.mdv.identity_service.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorCode {
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_KEY(400, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(400, "User already exists", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(400, "Username must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_DOB(400, "Your age must be at least 18", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(404, "User not found", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    UNAUTHORIZED(403, "You do not have permission", HttpStatus.FORBIDDEN),
    JWT_SIGNING_ERROR(500, "Error signing JWT", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int httpCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;
}
