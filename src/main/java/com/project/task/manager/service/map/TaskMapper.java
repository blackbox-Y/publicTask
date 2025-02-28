package com.project.task.manager.service.map;

import com.project.task.manager.domain.DTO.TaskDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.project.task.manager.domain.Task;

public class TaskMapper {
	
	public static TaskDTO toTaskDTO (Task task) {
		return new TaskDTO().builder()
				.taskTitle(task.getTitle())
				.description(task.getDescription())
				.steps(task.getSteps())
				.priority(task.getPriority())
				.status(task.getStatus())
				.agentName(task.getAgent().getEmail())
				.authorName(task.getAuthor().getEmail())
				.build();
	}
	
	public static Page <TaskDTO> toTaskPageDTO (Page <Task> taskPage) {
		
	     List<TaskDTO> taskDTOs = taskPage.getContent()
	                .stream()
	                .map(TaskMapper::toTaskDTO)
	                .collect(Collectors.toList());

	        return new PageImpl<>(taskDTOs,
	        		PageRequest.of(
	                		taskPage.getNumber(), 
	                		taskPage.getSize(), 
	                		taskPage.getSort()),
	                		taskPage.getTotalElements()
	        );
	}
}
