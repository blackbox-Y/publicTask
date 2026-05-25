//package com.project.task.manager;  // ✅ Оставь в корне или src/test/java/com/project/task/manager/service/
//
//import com.project.task.manager.domain.entities.User;
//import com.project.task.manager.domain.entities.VerificationToken;
//import com.project.task.manager.domain.exception.user.UserAlreadyExistsException;
//import com.project.task.manager.domain.request.RegisterRequest;
//import com.project.task.manager.domain.response.AuthenticationResponse;
//import com.project.task.manager.repository.UserRepository;
//import com.project.task.manager.repository.VerificationTokenRepository;
//import com.project.task.manager.security.JwtService;
//import com.project.task.manager.service.implementation.AuthenticationService;
//import com.project.task.manager.service.implementation.EmailService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@TestPropertySource("classpath:application-test.yml")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@Transactional
//class AuthenticationServiceTest {
//
//    @Autowired private AuthenticationService service;
//    @Autowired private UserRepository userRepo;
//    @Autowired private VerificationTokenRepository tokenRepo;
//    @MockitoBean private EmailService emailService;
//    @MockitoBean private JwtService jwtService;
//
//    @Test
//    @DisplayName("Register new user → saves + sends email + returns JWT")
//    void shouldRegisterNewUser() {
//        // Given
//        RegisterRequest request = new RegisterRequest(
//                "test@test.com",
//                "pass123",
//                "John",
//                "Doe",
//                null);  // Без Role!
//
//        when(jwtService.generateToken(any(User.class))).thenReturn("jwt.token");
//
//        // When
//        AuthenticationResponse response = service.register(request);
//
//        // Then
//        User savedUser = userRepo.findByEmail("test@test.com").orElseThrow();
//        assertThat(savedUser.isEnabled()).isFalse();
//        assertThat(savedUser.getEmail()).isEqualTo("test@test.com");
//        assertThat(response.getToken()).isEqualTo("jwt.token");
//
//        // Проверяем создание verification token
//        assertThat(tokenRepo.findAll()).hasSize(1);
//        verify(emailService).sendVerificationEmail(eq("test@test.com"), anyString());
//    }
//
//    @Test
//    @DisplayName("Register existing → throws UserAlreadyExistsException")
//    void shouldThrowOnDuplicateEmail() {
//        userRepo.save(User.builder()
//                .email("test@test.com")
//                .enabled(false)
//                .build());
//
//        RegisterRequest request = new RegisterRequest(
//                "test@test.com",
//                "pass123",
//                "John",
//                "Doe",
//                null);
//
//        assertThatThrownBy(() -> service.register(request))
//                .isInstanceOf(UserAlreadyExistsException.class)
//                .hasMessageContaining("test@test.com");
//    }
//
//    @Test
//    @DisplayName("Verify valid token → enables user + deletes token")
//    void shouldVerifyValidToken() {
//        // Given: создаём user + token
//        User user = userRepo.save(User.builder()
//                .email("test@test.com")
//                .enabled(false)
//                .build());
//        String token = "valid-token-123";
//        tokenRepo.save(VerificationToken.builder()
//                .token(token)
//                .user(user)
//                .expiritDate(LocalDateTime.now().plusHours(1))
//                .build());
//
//        // When
//        service.verifyEmail(token);
//
//        // Then
//        User updatedUser = userRepo.findById(user.getId()).orElseThrow();
//        assertThat(updatedUser.isEnabled()).isTrue();  // ✅ Активирован!
//
//        assertThat(tokenRepo.findByToken(token)).isEmpty();  // ✅ Удалён!
//    }
//}