package com.project.task.manager.service.interfaces;

import org.springframework.data.domain.Page;

import com.project.task.manager.domain.entities.Comments;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;

public interface CommentService {
	Comments addComment (CommentDTO commentDTO, Task task, User user);
	
	Page <CommentDTO> showTaskComments (
			Task task,
			User user,
			int pageNumber, 
			int pageSize
			);
}
