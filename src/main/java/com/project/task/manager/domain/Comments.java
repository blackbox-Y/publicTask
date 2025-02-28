package com.project.task.manager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity (name = "comment")
@Table (name = "comment_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode (of = "id")
public class Comments {
	@Id
	@Column(
			name = "id",
			updatable = false,
			unique = true)
	@SequenceGenerator(
			name = "task_Id_sequence",
			sequenceName = "task_Id_sequence",
			allocationSize = 3)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "task_Id_sequence")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "commenter_id", referencedColumnName = "id")
	private User commenter;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn (name = "task_id", referencedColumnName = "id"),
		@JoinColumn (name = "title", referencedColumnName = "title")
		})
	private Task task;
	
	@Column(name = "text")
	private String text;
	
	
	
}
