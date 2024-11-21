package br.com.fiap.simec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("argos.ai.fiap@gmail.com"); // Email de destino
        mailMessage.setSubject(subject); // Assunto do e-mail
        mailMessage.setText(message); // Conteúdo do e-mail

        mailSender.send(mailMessage);
    }
}
