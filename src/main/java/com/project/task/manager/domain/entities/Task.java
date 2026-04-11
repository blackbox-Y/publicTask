package com.project.task.manager.domain.entities;

import com.project.task.manager.domain.status.PRIORITY;
import com.project.task.manager.domain.status.STATUS;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Task {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @SequenceGenerator(
            name = "task_id_sequence",
            sequenceName = "task_id_sequence",
            allocationSize = 3 // allocationSize = 50 for production
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_id_sequence"
    )
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private User agent;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comments> comments = new ArrayList<>();


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;


    @ElementCollection
    @CollectionTable(
            name = "task_steps",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "step_description")
    @OrderColumn(name = "step_order")
    @Builder.Default
    private LinkedList<String> steps = new LinkedList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PRIORITY priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private STATUS status;

    public void addComment(Comments comment) {
        comments.add(comment);
        comment.setTask(this);
    }

    public void removeComment(Comments comment) {
        comments.remove(comment);
        comment.setTask(null);
    }

    public Long getAuthorId() {
        return author != null ? author.getId() : null;
    }

    public Long getAgentId() {
        return agent != null ? agent.getId() : null;
    }

    public List<Long> getCommentIds() {
        return comments.stream()
                .map(Comments::getId)
                .toList();
    }

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

        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id= " + id +
                ", title= '" + title + '\'' +
                ", priority= " + priority +
                ", status= " + status +
                '}';
    }
}