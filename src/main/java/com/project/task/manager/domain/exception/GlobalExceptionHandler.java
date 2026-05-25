package com.project.task.manager.domain.exception;

import com.project.task.manager.domain.exception.user.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponse> handleUserExists (UserAlreadyExistsException e) {

        //return ResponseEntity.status(409).body(new ErrorRsponse(""));
        // todo ErrorResponse, GlobalExceptionHandler, exceptions in general
    return null;
    }
}