package com.moodly.user.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Override
    public void sendFindIdCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("아이디 찾기 인증코드");
        message.setText("인증 코드 : " + code + "\n 유효시간: 5분");

        mailSender.send(message);
    }

    @Override
    public void sendPasswordResetLink(String email, String link) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("비밀번호 재설정 링크");

            String htmlContent = "<html><body style='font-family: Arial, sans-serif; padding: 20px;'>" +
                    "<p>아래 링크를 클릭해서 비밀번호를 재설정하세요.</p>" +
                    "<p style='margin: 20px 0;'>" +
                    "<a href='" + link + "' target='_blank' " +
                    "style='color: #007bff; text-decoration: underline; word-break: break-all;'>" + link + "</a>" +
                    "</p>" +
                    "<p style='color: #666; font-size: 12px;'>유효시간: 15분</p>" +
                    "</body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.", e);
        }
    }
}
