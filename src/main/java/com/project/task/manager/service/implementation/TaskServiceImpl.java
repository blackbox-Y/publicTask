package com.project.task.manager.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.task.manager.constants.ErrorMessage;
import com.project.task.manager.domain.Comments;
import com.project.task.manager.domain.Task;
import com.project.task.manager.domain.User;
import com.project.task.manager.domain.DTO.CommentDTO;
import com.project.task.manager.domain.DTO.TaskDTO;
import com.project.task.manager.domain.DTO.TaskWithCommentsDTO;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.repository.TaskRepository;
import com.project.task.manager.security.TaskSecurityService;
import com.project.task.manager.service.TaskService;
import com.project.task.manager.service.UserService;
import com.project.task.manager.service.map.TaskMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{
	
	private final CommentServiceImpl commentService;
	private final UserService userService;
	
	private final TaskRepository taskRepo;
	
	
	
	@Override
	@PreAuthorize("authentication.principal.id == #id")
	public TaskDTO findTaskDTO (Long taskId, Long userId) {
		Task task = taskRepo.findById(taskId).orElseThrow(
				()-> new EntityNotFoundException(ErrorMessage.TASK_NOT_FOUND));
		return TaskMapper.toTaskDTO(task);
	}
	
	@Override
	public Task findTask (Long taskId, Long userId) {
		return taskRepo.findById(taskId).orElseThrow(
				()-> new EntityNotFoundException(ErrorMessage.TASK_NOT_FOUND));
	}

	@Override
	@PreAuthorize("authentication.principal.id == #authorId")
	public Page<TaskDTO> findAuthorTasks(Long authorId, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		User user = userService.findById(authorId);
		
		Page <Task> taskPage = taskRepo.findByAuthor(pageable, user);
		Page<TaskDTO> dtoTaskPage = TaskMapper.toTaskPageDTO(taskPage);
		
		return dtoTaskPage;
	}

	@Override
	@PreAuthorize("authentication.principal.id == #authorId")
	public String setStatus(STATUS status, Long taskId, Long authorId) {
			findTask(taskId, authorId).setStatus(status);
			return "Status updated: " + status;
	}

	@Override
	@PreAuthorize("authentication.principal.id == #authorId")
	public Comments addComments(CommentDTO comDTO, Long taskId, Long authorId) {	
			Task task = findTask(taskId, authorId);
			User user = userService.findById(authorId);
			
			return commentService.addComment(comDTO, task, user);
	}
	

	@Override
	@PreAuthorize("authentication.principal.id == #authorId")
	public TaskWithCommentsDTO taskWithComments(Long taskId, Long authorId, int pageNumber, int pageSize) {
		TaskDTO task = findTaskDTO(taskId, authorId);
		Page <CommentDTO> comPage = showTaskComments(taskId, authorId, pageNumber, pageSize);
		
		TaskWithCommentsDTO taskCom = new TaskWithCommentsDTO(task, comPage); 
		
		return taskCom;
	}

	@Override
	@PreAuthorize("authentication.principal.id == #authorId")
	public Page<CommentDTO> showTaskComments(
			Long taskId, 
			Long authorId, 
			int pageNumber, 
			int pageSize) {
		Task task = findTask(taskId, authorId);
		User user = userService.findById(authorId);
		Page <CommentDTO> commentPage = commentService
				.showTaskComments(task, user, pageNumber, pageSize);
		return commentPage;
	}

}
