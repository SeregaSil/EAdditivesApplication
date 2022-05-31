package com.example.e_additives.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("email")
    private String email;

    public void sendEmail(String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("E-Additives");
        message.setText(body);

        mailSender.send(message);
    }
}
