package com.mdv.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mdv.identity_service.dto.response.ApiRespone;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiRespone<Void>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ApiRespone<>(400, e.getMessage(), null));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespone<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = (fieldError != null) ? fieldError.getDefaultMessage() : "Invalid request";
        return ResponseEntity.badRequest().body(new ApiRespone<>(400, message, null));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiRespone<Object>> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(403).body(
                ApiRespone.builder().code(403).message("You do not have permision").build());
    }
}
