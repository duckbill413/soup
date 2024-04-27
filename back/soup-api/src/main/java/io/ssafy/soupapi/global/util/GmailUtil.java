package io.ssafy.soupapi.global.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class GmailUtil {
    private final JavaMailSender mailSender;

    /**
     * Gmail 발송
     * @param to 수신자 이메일
     * @param from 발신자 정보
     * @param subject 이메일 제목
     * @param body 이메일 본문
     * @param html true: html code, false: text
     * @throws MessagingException
     */
    public void sendMail(String to, String from, String subject, String body, boolean html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(body, html);
        mailSender.send(message);
    }
}
