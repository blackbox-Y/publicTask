package com.project.task.manager.domain.exception.authemtication;

import com.project.task.manager.domain.exception.application.BusinessException;

public class AuthenticationException extends BusinessException {
    private final AuthErrorCode authErrorCode;

    public enum AuthErrorCode {
        INVALID_CREDENTIALS("AUTH_001"),
        ACCOUNT_LOCKED("AUTH_002"),
        ACCOUNT_DISABLED("AUTH_003"),
        INVALID_TOKEN("AUTH_004"),
        TOKEN_EXPIRED("AUTH_005"),
        INSUFFICIENT_PERMISSIONS("AUTH_006");

        private final String code;

        AuthErrorCode(String code) {
            this.code = code;
        }
    }


    public AuthenticationException(AuthErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.code, message, cause);
        this.authErrorCode = errorCode;
    }
}