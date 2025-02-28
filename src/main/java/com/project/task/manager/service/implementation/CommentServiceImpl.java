package com.project.task.manager.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.Comments;
import com.project.task.manager.domain.Task;
import com.project.task.manager.domain.User;
import com.project.task.manager.domain.DTO.CommentDTO;
import com.project.task.manager.domain.DTO.TaskDTO;
import com.project.task.manager.repository.CommentRepository;
import com.project.task.manager.repository.TaskRepository;
import com.project.task.manager.repository.UserRepository;
import com.project.task.manager.service.CommentService;
import com.project.task.manager.service.map.CommentMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository repo;
	
	@Override
	public Comments addComment (CommentDTO commentDTO, Task task, User user) {
		Comments comment = new Comments().builder()
				.task(task)
				.commenter(user)
				.text(commentDTO.getText())
				.build();
		return comment;
	}
	
	@Override
	public Page<CommentDTO> showTaskComments(
			Task task, 
			User user, 
			int pageNumber, 
			int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<CommentDTO> comPage = CommentMapper
				.toComPageDTO(repo.findByTask(task, pageable));
		return comPage;
	}
	

}
