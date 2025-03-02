package com.mdv.identity_service.exception;

public class JWTSigningException extends RuntimeException {
    public JWTSigningException(String message) {
        super(message);
    }
}
