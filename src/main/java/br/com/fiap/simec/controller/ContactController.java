package br.com.fiap.simec.controller;

import br.com.fiap.simec.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private MessagingService messagingService;

    @GetMapping
    public String showContactForm(Model model) {
        return "contact"; // Certifique-se de que contact.html está no diretório correto
    }

    @PostMapping("/sendMessage")
    public String sendMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {

        messagingService.sendMessage(message);

        model.addAttribute("confirmationMessage", "Mensagem enviada com sucesso!");

        return "contact";
    }
}
