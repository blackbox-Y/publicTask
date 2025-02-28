package com.project.task.manager.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.task.manager.constants.ErrorMessage;
import com.project.task.manager.domain.Task;
import com.project.task.manager.domain.User;
import com.project.task.manager.domain.DTO.CommentDTO;
import com.project.task.manager.domain.DTO.TaskDTO;
import com.project.task.manager.domain.DTO.TaskWithCommentsDTO;
import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.repository.TaskRepository;
import com.project.task.manager.service.AdminService;
import com.project.task.manager.service.UserService;
import com.project.task.manager.service.map.TaskMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImlp implements AdminService{
	private final TaskRepository taskRepo;
	private final CommentServiceImpl comServ;
	private final UserService userService;
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Task addTask(Task task) {
		taskRepo.save(task);
		return task;
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Task findTask(Long taskId) {
		return taskRepo.findById(taskId).orElseThrow(
				()-> new EntityNotFoundException(ErrorMessage.TASK_NOT_FOUND));
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Task findTask(String TaskTitle) {
		return taskRepo.findByTitle(TaskTitle).orElseThrow(
				()-> new EntityNotFoundException(ErrorMessage.TASK_NOT_FOUND));
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Page<TaskDTO> findAllTasks(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<TaskDTO> dtoTaskPage = TaskMapper
				.toTaskPageDTO(taskRepo.findAll(pageable));
		;
		return dtoTaskPage;
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Page<TaskDTO> findAgentTasks(Long id, int pageNumber, int pageSize) {
		User user = userService.findById(id);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<TaskDTO> dtoTaskPage = TaskMapper
				.toTaskPageDTO(taskRepo.findByAgent(pageable, user));
		
		return dtoTaskPage;
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Page<TaskDTO> findAuthorTasks(Long id, int pageNumber, int pageSize) {
		User user = userService.findById(id);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<TaskDTO> dtoTaskPage = TaskMapper
				.toTaskPageDTO(taskRepo.findByAuthor(pageable, user));
		
		return dtoTaskPage;
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public String setStatus(STATUS status, Long Id) {
		findTask(Id).setStatus(status);
		return status.toString()+ " was set";
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public String setPriority(PRIORITY priority, Long Id) {
		findTask(Id).setPriority(priority);
		return priority.toString()+ " was set";
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public String setAgent(Long userId, Long taskId) {
		User user = userService.findById(userId);
		
		findTask(taskId).setAgent(user);
		return user.getName()+ "is an agent";
	}
	
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public String addComments(CommentDTO comDTO, Long taskId, Long userId) {
		Task task = findTask(taskId);
		User user = userService.findById(userId);
		comServ.addComment(comDTO, task, user);
		return comDTO.getText();
	}
	
	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteTask(Long Id) {
		Task task = findTask(Id);
		taskRepo.deleteById(Id);
		return task.getTitle() + " task is deletet";
	}

	@Override
	public TaskWithCommentsDTO taskWithComments(Long Id, Long userId, int pageNumber, int pageSize) {
		Task task = findTask(Id);
		TaskDTO taskDTO = TaskMapper.toTaskDTO(task);
		Page <CommentDTO> comPage = showTaskComments(Id, userId, pageNumber, pageSize);
		
		TaskWithCommentsDTO taskCom = new TaskWithCommentsDTO(taskDTO, comPage); 
		
		return taskCom;
	}

	@Override
	public Page<CommentDTO> showTaskComments(Long taskId, Long userId, int pageNumber, int pageSize) {
		Task task = findTask(taskId);
		User user = userService.findById(userId);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<CommentDTO> taskComments = comServ.showTaskComments(task, user, pageNumber, pageSize);
		
		return taskComments;
	}
	}
