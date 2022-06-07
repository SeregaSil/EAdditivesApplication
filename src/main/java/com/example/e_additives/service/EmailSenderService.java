package com.example.e_additives.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Класс-сервис для отправки сообщения об ошибке.
 * Содержит поля:
 * <b>mailSender</b> - интерфейс {@link JavaMailSender}, который предлагает простую абстракцию для отправки электронной почты;
 * <b>emailFrom</b> - электронный почтовый адрес, с которого отправляется письмо;
 * <b>emailTo</b> - электронный почтовый адрес, на который отправляется письмо.
 */
@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${emailTo}")
    private String emailTo;

    /**
     * Конструктор.
     * @param mailSender интерфейс для отправки простого сообщения.
     */
    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Метод, который создает и отправляет сообщение на электронную почту.
     * @param body тело сообщения.
     */
    public void sendEmail(String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject("E-Additives");
        message.setText(body);

        mailSender.send(message);
    }
}
