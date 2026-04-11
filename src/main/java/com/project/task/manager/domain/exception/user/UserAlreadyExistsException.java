package com.project.task.manager.domain.exception.user;

public class UserAlreadyExistsException extends UserException{
    private final String email;

    public UserAlreadyExistsException(String email) {
        super(UserErrorCode.USER_ALREADY_EXISTS, "User with email: " + email + " already excists", null);
        this.email = email;
    }
}
