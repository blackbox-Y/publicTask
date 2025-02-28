package com.project.task.manager.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.manager.domain.Task;
import com.project.task.manager.domain.DTO.CommentDTO;
import com.project.task.manager.domain.DTO.TaskDTO;
import com.project.task.manager.domain.DTO.TaskWithCommentsDTO;
import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;
import com.project.task.manager.service.implementation.AdminServiceImlp;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;


@Tag(name = "main_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/task")
public class AdminController {
	private final AdminServiceImlp service;
	
	@PostMapping("/add")
	public Task addTask (@RequestParam Task task) {
		return service.addTask(task);
	}
	
	@GetMapping("/{id}")
	public Task findTask (@PathVariable Long id) {
		return service.findTask(id);
	}
	
	@GetMapping("/{title}")
	public Task findTask (@PathVariable String title) {
		return service.findTask(title);
	}
	
	@GetMapping("/show/{pageNumber}")
	public Page <TaskDTO> findAllTask (@PathVariable int pageNumber, @RequestParam int pageSize) {
		return service.findAllTasks(pageNumber, pageSize);
	}
	
	@GetMapping("/show/agent:{id}/{pageNumber}")
	public Page <TaskDTO> findAllAgentTask (@PathVariable int pageNumber, @RequestParam int pageSize, @RequestParam Long id) {
		return service.findAgentTasks(id, pageNumber, pageSize);
	}
	
	@GetMapping("/show/author:{id}/{pageNumber}")
	public Page <TaskDTO> findAllAuthorTask (@PathVariable int pageNumber, @RequestParam int pageSize, @RequestParam Long id) {
		return service.findAuthorTasks(id, pageNumber, pageSize);
	}
	
	@PatchMapping("/update/status/{id}")
	public String setStatus (@PathVariable Long id, @RequestParam STATUS status) {
		return service.setStatus(status, id);
	}
	
	@PatchMapping("/update/priority/{id}")
	public String setPriority (@PathVariable Long id, @RequestParam PRIORITY prority) {
		return service.setPriority(prority, id);
	}
	
	@PatchMapping("/update/agent/{id}")
	public String setAgent (@PathVariable Long id, @RequestParam Long userId) {
		return service.setAgent(userId, id);
	}
	
	@PostMapping("/add-comment/{id}")
	public String addComments (@PathVariable Long id, @RequestParam CommentDTO comment, @RequestParam  Long userId) {
		return service.addComments(comment, id, userId);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteTask (@PathVariable Long id) {
		return service.deleteTask(id);
	}
	
	@GetMapping("/show/{id}/page-{pageNumber}")
	public TaskWithCommentsDTO findTaskWithComments (
			@PathVariable Long id, 
			@RequestParam Long userId, 
			@RequestParam int PageSize, 
			@PathVariable int pageNumber
			) {
		return service.taskWithComments(id, userId, pageNumber, PageSize);
	}
	
	@GetMapping("/{id}/page-{pageNumber}")
	public Page<CommentDTO> showTaskComments(
			@PathVariable Long id, 
			@RequestParam Long userId, 
			@RequestParam int PageSize, 
			@PathVariable int pageNumber
			) {
		return service.showTaskComments(id, userId, pageNumber, PageSize);
	}
	
}
