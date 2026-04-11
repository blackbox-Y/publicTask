package com.project.task.manager.exception.authemtication;

import lombok.Getter;

import java.time.Instant;

@Getter
public class AccountLockedException extends AuthenticationException {
    private final Instant lockedUntil;

    public AccountLockedException(Instant lockedUntil) {
        super(
                AuthErrorCode.ACCOUNT_LOCKED,
                "Account is locked until: " + lockedUntil,
                null
        );
        this.lockedUntil = lockedUntil;
    }

}
