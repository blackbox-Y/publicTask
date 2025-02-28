package com.project.task.manager.service;

import org.springframework.data.domain.Page;

import com.project.task.manager.domain.Task;
import com.project.task.manager.domain.User;
import com.project.task.manager.domain.DTO.CommentDTO;
import com.project.task.manager.domain.DTO.TaskDTO;
import com.project.task.manager.domain.DTO.TaskWithCommentsDTO;
import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;

public interface AdminService {
	Task addTask (Task task);
	
	
	Task findTask (Long taskId);
	
	Task findTask (String TaskTitle);
	
	Page<CommentDTO> showTaskComments(
			Long taskId,
			Long userId,
			int pageNumber, 
			int pageSize);
	
	TaskWithCommentsDTO taskWithComments (Long Id, Long userId, int pageNumber, int pageSize); 
	
	
	Page <TaskDTO> findAllTasks (int pageNumber, int pageSize);
	
	Page <TaskDTO> findAgentTasks (Long id, int pageNumber, int pageSize);
	
	Page <TaskDTO> findAuthorTasks (Long authorId, int pageNumber, int pageSize);

	
	String setStatus (STATUS status, Long Id);
	
	String setPriority (PRIORITY priority, Long Id);
	
	String setAgent (Long userId, Long taskId);
	
	
	String addComments (CommentDTO comDTO, Long authorId, Long userId);
	
	
	String deleteTask (Long Id);
}
