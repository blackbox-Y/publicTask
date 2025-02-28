package com.project.task.manager.domain.DTO;

import java.util.LinkedList;

import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TaskDTO {
	private String taskTitle;
	private String description;
	private LinkedList <String> steps;
	
	private PRIORITY priority;
	private STATUS status;
	
	private String authorName;
	private String agentName;
}
