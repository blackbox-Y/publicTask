package com.project.task.manager.service.implementation;


import com.project.task.manager.domain.entities.VerificationToken;
import com.project.task.manager.domain.exception.authemtication.InvalidCredentialsException;
import com.project.task.manager.domain.exception.user.UserAlreadyExistsException;
import com.project.task.manager.repository.VerificationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.request.AuthenticationRequest;
import com.project.task.manager.domain.response.AuthenticationResponse;
import com.project.task.manager.domain.request.RegisterRequest;
import com.project.task.manager.domain.status.Role;
import com.project.task.manager.repository.UserRepository;
import com.project.task.manager.security.JwtService;

import lombok.RequiredArgsConstructor;

import com.project.task.manager.domain.entities.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repo;
	private final JwtService jwtService;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository tokenRepository;

//    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) {
        log.info("attempt to register user: {}", request.getEmail());

        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());}

		// creating USER
        var user = User.builder()
				.name(request.getName())
				.surname(request.getSurname())
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.role(Role.USER)
                .enabled(false)
				.build();
        repo.save(user);

        // creating JWT token
		var jwtToken = jwtService.generateToken(user);
        log.info("User registered: {}", request.getEmail());

        // creating verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiritDate(LocalDateTime.now().plusHours(24))
                .build();
        tokenRepository.save(verificationToken);

//        emailService.sendVerificationEmail(user.getEmail(), token);

        // JWT token return
        return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse  authenticate(AuthenticationRequest request) {
        log.info("attempt to authenticate user: {}", request.getEmail());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));

        validateAuthentication(request);
		var user = repo.findByEmail(request.getEmail()).orElseThrow(EntityNotFoundException::new);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

    public void verifyEmail (String token) {
        log.info("Token verification: {}",token);

        VerificationToken vToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("invalid token"));

        if (vToken.getExpiritDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token expired");

        vToken.getUser().setEnabled(true);
        repo.save(vToken.getUser());
        tokenRepository.delete(vToken);

        log.info("Email is verified: {}", vToken.getUser().getEmail());
    }

    @Transactional
    public void requestPasswordReset (String email) {
        log.info("Password reset request for: {}", email);

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Генерим новый token (или используй отдельную таблицу ResetToken)
        String token = UUID.randomUUID().toString();
        VerificationToken resetToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        tokenRepository.save(resetToken);

//        emailService.sendResetPassword(email, token);
    }

    @Transactional
    public void resetPassword (String token, String newPassword) {
        log.info("Сброс пароля по token: {}", token);

        VerificationToken vToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Неверный токен"));

        if (vToken.getExpiritDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Токен истёк");


        vToken.getUser().setPassword(encoder.encode(newPassword));
        repo.save(vToken.getUser());

        tokenRepository.delete(vToken);
    }


    private void validateAuthentication (AuthenticationRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new InvalidCredentialsException(null);
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new InvalidCredentialsException(null);
        }
    }

}
