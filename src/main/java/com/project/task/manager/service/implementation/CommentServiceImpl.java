package com.project.task.manager.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.entities.Comments;
import com.project.task.manager.domain.entities.Task;
import com.project.task.manager.domain.entities.User;
import com.project.task.manager.repository.CommentRepository;
import com.project.task.manager.service.interfaces.CommentService;
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
