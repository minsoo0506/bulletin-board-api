package com.mnsoo.board.service;

import com.mnsoo.board.domain.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificationService {

    private final JavaMailSender javaMailSender;

    public EmailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendWarning(User user, String postTitle) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            log.info("Sending warning email to '{}'", user.getEmail());

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(user.getEmail());
            helper.setSubject("게시글 수정 불가 사전 알림");
            helper.setText("[" + postTitle + "]" + "을 작성하신지 9일째 입니다. 게시글 등록 이후 10일이 지나면 수정이 불가합니다.");
        } catch (MessagingException e) {
            log.warn("Failed to send warning mail : '{}'", user.getEmail());

            throw new RuntimeException(e);
        }
    }
}
