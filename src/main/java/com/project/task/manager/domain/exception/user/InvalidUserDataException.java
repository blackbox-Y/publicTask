package com.project.task.manager.domain.exception.user;

import lombok.Getter;

import java.util.Map;
@Getter
public class InvalidUserDataException extends UserException {
    private final Map<String, String> validationException;

    public InvalidUserDataException (Map <String, String> validationException) {
       super(UserErrorCode.INVALID_USER_DATA, "Invalid user data: " + validationException, null);
       this.validationException = validationException;
    }
}
