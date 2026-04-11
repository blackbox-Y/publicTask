package com.project.task.manager.exception.jwt;

import lombok.Getter;

import java.util.Date;

@Getter
public class TokenExpiredException extends JwtException {
    private final Date expirationTime;

    public TokenExpiredException(Date expirationTime) {
        super(
                JwtErrorCode.TOKEN_EXPIRED,
                "Token expired at: " + expirationTime,
                null
        );
        this.expirationTime = new Date(expirationTime.getTime());
    }
}
