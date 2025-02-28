package com.project.task.manager.domain.DTO;

import com.project.task.manager.domain.DTO.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.domain.Page;


@AllArgsConstructor
@Getter
@Setter
public class TaskWithCommentsDTO {
	private final TaskDTO task;
	private final Page <CommentDTO> comments;
}
