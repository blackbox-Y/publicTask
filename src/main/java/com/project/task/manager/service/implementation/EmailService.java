//package com.project.task.manager.service.implementation;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Slf4j
////@Service
////@RequiredArgsConstructor
//public class EmailService {
//
////    private final JavaMailSender sender;
//
//    @Value("${app.frontend-url:http://localhost:3000}")
//    private String frontendURL;
//
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    @Async
//    public void sendVerificationEmail(String to, String token) {
//        log.info("Отправка verification email на: {}", to);
//        String url = frontendURL + "/verify?token=" + token;
//        String subject = "Подтвердите ваш email - TaskManager";
//        String htmlBody = """
//                <h2>Добро пожаловать в TaskManager!</h2>
//                <p>Подтвердите email, перейдя по ссылке:</p>
//                <a href="%s" style="background: #007bff; color: white; padding: 10px 20px;
//                    text-decoration: none; border-radius: 5px; display: inline-block;">
//                    Подтвердить email
//                </a>
//                <p>Ссылка действительна 24 часа.</p>
//                <hr>
//                <p>Если вы не регистрировались, игнорируйте это письмо.</p>
//                """.formatted(url);
//        sendHtmlEmail(to, subject, htmlBody);
//    }
//
//    @Async
//    public void sendResetPassword(String to, String token) {
//        log.info("Отправка reset password на: {}", to);
//        String url = frontendURL + "/reset-password?token=" + token;
//        String subject = "Сброс пароля - TaskManager";
//        String htmlBody = """
//                <h2>Сброс пароля</h2>
//                <p>Вы запросили сброс пароля. Перейдите по ссылке:</p>
//                <a href="%s" style="background: #dc3545; color: white; padding: 10px 20px;
//                    text-decoration: none; border-radius: 5px; display: inline-block;">
//                    Сбросить пароль
//                </a>
//                <p>Ссылка действительна 24 часа. Если не запрашивали, игнорируйте.</p>
//                """.formatted(url);
//
//        sendHtmlEmail(to, subject, htmlBody);
//        log.info("Reset email отправлен на: {}", to);
//    }
//
//    @Async
//    public void sendHtmlEmail(String to, String subject, String htmlBody) {
//        try {
//            MimeMessage message = sender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setFrom(fromEmail);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(htmlBody, true);
//
//            sender.send(message);
//            log.info("Email успешно отправлен на: {}", to);
//        } catch (MessagingException e) {
//            log.error("Ошибка отправки email на {}: {}", to, e.getMessage());
//            throw new RuntimeException("Не удалось отправить email на " + to, e);
//        }
//    }
//}