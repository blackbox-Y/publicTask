package com.project.task.manager.exception.jwt;

public class InvalidTokenException extends JwtException {
    public InvalidTokenException(String token, Throwable cause) {
        super(
                JwtErrorCode.MALFORMED_TOKEN,
                "Invalid token format: " + token,
                cause
        );
    }
}
