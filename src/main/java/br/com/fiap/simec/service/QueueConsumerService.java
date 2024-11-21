package br.com.fiap.simec.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueConsumerService {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void consumeMessage(String message) {
        System.out.println("Mensagem consumida da fila: " + message);

        String subject = "Nova mensagem do RabbitMQ";
        emailService.sendEmail(subject, message);
    }
}
