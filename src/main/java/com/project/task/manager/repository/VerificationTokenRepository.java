package com.project.task.manager.repository;

import com.project.task.manager.domain.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository <VerificationToken, Long> {
    Optional <VerificationToken> findByToken (String token);
}
