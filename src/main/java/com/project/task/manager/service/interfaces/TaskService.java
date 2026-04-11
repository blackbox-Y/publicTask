package com.project.task.manager.service.interfaces;

import org.springframework.data.domain.Page;

import com.project.task.manager.domain.entities.Comments;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.status.STATUS;

public interface TaskService {
	
	TaskDTO findTaskDTO (Long taskId, Long authorId);
	
	Task findTask (Long taskId, Long authorId);
	
	TaskWithCommentsDTO taskWithComments (Long taskId, Long authorId, int pageNumber, int pageSize);

	Page <TaskDTO> findAuthorTasks (Long authorId, int pageNumber, int pageSize);
	
	String setStatus (STATUS status, Long taskId, Long authorId);
	
	Comments addComments (CommentDTO comDTO, Long taskId, Long authorId);
	
	Page <CommentDTO> showTaskComments (Long taskId, Long authorId, int pageNumber, int pageSize);
}
