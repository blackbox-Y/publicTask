package com.project.task.manager.domain.exception.authemtication;

public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException (Throwable cause) {
        super(AuthErrorCode.INVALID_CREDENTIALS, "invalid email or password", cause);
    }
}
