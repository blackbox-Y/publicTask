package com.project.task.manager.constants;

import lombok.Getter;

@Getter
public class TaskNotFoundException extends RuntimeException {
    private Long taskId;

    public TaskNotFoundException(Long taskId) {
        super("Task with id " + taskId + "not found");
        this.taskId = taskId;
    }
}