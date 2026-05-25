package com.project.task.manager.domain.exception;

public class TaskNotFoundException extends RuntimeException  {
    public TaskNotFoundException(Long taskId) {
    }
}
