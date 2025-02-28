package com.project.task.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.task.manager.constants.TaskNotFoundException;
import com.project.task.manager.domain.Task;
import com.project.task.manager.repository.TaskRepository;

@Service
public class TaskSecurityService {

    @Autowired
    private  TaskRepository taskRepository;
    
//    public boolean canAccessTask (Long userId, String title) {
//    	Long taskId = taskRepository.findByTitle(title).getId();
//    	return canAccessTask(userId, taskId);
//                
//    }

    public boolean canAccessTask(Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return task.getAuthor().getId().equals(userId);
    }
}
