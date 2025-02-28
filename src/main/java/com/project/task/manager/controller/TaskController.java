package com.project.task.manager.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.manager.domain.Comments;
import com.project.task.manager.domain.DTO.CommentDTO;
import com.project.task.manager.domain.DTO.TaskDTO;
import com.project.task.manager.domain.DTO.TaskWithCommentsDTO;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.service.implementation.TaskServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "demo_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/demo")
public class TaskController {
	private final TaskServiceImpl service;
	
	@Operation(summary = "ищет задачу по ID")
	
	@GetMapping("/search/{id}")
	public TaskDTO findTask (@RequestParam Long userId, @PathVariable Long taskId) {
		return service.findTaskDTO(taskId, userId);
	}
	
	@GetMapping("/my/{id}")
	public Page<TaskDTO> findAuthotTask (@RequestParam Long authorId, @RequestParam int pageSize, @PathVariable int pageNumber) {
		return service.findAuthorTasks(authorId, pageSize, pageNumber);
	}
	@GetMapping("/my/{id}-set")
	public String setStatus (@RequestParam STATUS status, @PathVariable Long taskId, @RequestParam Long authorId) {
		return service.setStatus(status, taskId, authorId);
	}
	@GetMapping("/my/{id}-comment")
	public Comments addComments (@RequestParam CommentDTO comDTO, @PathVariable Long taskId, @RequestParam  Long authorId) {
		return service.addComments(comDTO, taskId, authorId);
	}
	@GetMapping("/full/{id}")
	public TaskWithCommentsDTO taskWithComments (@RequestParam Long taskId, Long authorId, @PathVariable int pageNumber, @RequestParam int pageSize) {
		return service.taskWithComments(taskId, authorId, pageNumber, pageSize);
	}
	@GetMapping("/page/{id}")
	public Page<CommentDTO> showTaskComments (@RequestParam Long taskId, Long authorId, @PathVariable int pageNumber, @RequestParam int pageSize) {
		return service.showTaskComments(taskId, authorId, pageNumber, pageSize);
	}
	
}
