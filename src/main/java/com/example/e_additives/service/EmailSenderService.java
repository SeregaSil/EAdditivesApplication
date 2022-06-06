package com.example.e_additives.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Класс-сервис для отправки сообщения об ошибке.
 * <b>mailSender<b/>
 */
@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${emailTo}")
    private String emailTo;

    public void sendEmail(String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject("E-Additives");
        message.setText(body);

        mailSender.send(message);
    }
}
