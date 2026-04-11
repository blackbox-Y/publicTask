package com.project.task.manager.exception.jwt;

public class TokenCreationException extends JwtException {

    public TokenCreationException(String message, Throwable cause) {
        super(
                JwtErrorCode.TOKEN_CREATION_FAILED,
                message,
                cause
        );
    }
}