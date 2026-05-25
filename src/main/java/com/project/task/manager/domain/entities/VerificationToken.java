package com.project.task.manager.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    @Id @GeneratedValue private Long id;
    private String token;
    @ManyToOne private User user;
    private LocalDateTime expiritDate;
}
