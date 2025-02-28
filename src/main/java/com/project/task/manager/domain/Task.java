package com.project.task.manager.domain;


import java.util.LinkedList;
import java.util.List;

import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity (name = "task")
@Data
@Table (name = "task_table")
@NoArgsConstructor
@EqualsAndHashCode (of = "id")

public class Task {
	@Id
	@Column(
			name = "id",
			updatable = false,
			unique = true)
	@SequenceGenerator(
			name = "task_id_sequence",
			sequenceName = "task_id_sequence",
			allocationSize = 3)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "task_Id_sequence")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "agent_id", referencedColumnName = "id")
	private User agent;
	
	@OneToMany
	@JoinColumn(name = "comments_id", referencedColumnName = "id")
	private List <Comments> comments;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "steps")
	private LinkedList <String> steps;
	
	@CollectionTable(
			name = "task_priority",
			joinColumns = @JoinColumn(
					name = "task_id"
					)
			)
	@Enumerated(EnumType.STRING)
	private PRIORITY priority;
	
	@CollectionTable(
			name = "task_status",
			joinColumns = @JoinColumn(
					name = "task_status"
					)
			)
	@Enumerated(EnumType.STRING)
	private STATUS status;

}
