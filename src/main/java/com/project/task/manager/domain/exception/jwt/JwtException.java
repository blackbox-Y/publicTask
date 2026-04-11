package com.project.task.manager.domain.exception.jwt;


import com.project.task.manager.domain.exception.application.TechnicalException;

public class JwtException extends TechnicalException {
    private final JwtErrorCode jwtErrorCode;

    public enum JwtErrorCode {
        TOKEN_EXPIRED("JWT_001"),
        INVALID_SIGNATURE("JWT_002"),
        MALFORMED_TOKEN("JWT_003"),
        UNSUPPORTED_TOKEN("JWT_004"),
        TOKEN_CREATION_FAILED("JWT_005");

        private final String code;

        JwtErrorCode(String code) {
            this.code = code;
        }
    }

    public JwtException(JwtErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.code, message, cause);
        this.jwtErrorCode = errorCode;
    }
}
