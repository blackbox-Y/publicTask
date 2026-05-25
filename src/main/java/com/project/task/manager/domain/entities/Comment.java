package com.project.task.manager.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comment {
	@Id
	@Column(
			name = "id",
			updatable = false,
			unique = true,
            nullable = false
    )
	@SequenceGenerator(
			name = "comments_id_sequence",
			sequenceName = "comments_id_sequence",
			allocationSize = 3
    )
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "comments_id_sequence"
    )
	private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commenter_id")
	private User commenter;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "task_id")
	private Task task;
	
	@Column(name = "text", nullable = false)
	private String text;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy
                        ? proxy.getHibernateLazyInitializer().getPersistentClass()
                        : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy
                        ? proxy.getHibernateLazyInitializer().getPersistentClass()
                        : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Comment comments = (Comment) o;
        return getId() != null && Objects.equals(getId(), comments.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }


    public Long getCommenterId() { return commenter != null ? commenter.getId() : null; }

    public Long getTaskId() { return task != null ? task.getId() : null;}

    @Override
    public String toString () {
        return "Comment{ " +
                "id= " + id +
                ", createdAt= " + createdAt +
                ", updatedAt= " + updatedAt +
                ", text=" + text +
                "}";
    }
}
