package br.com.fiap.simec.controller;

import br.com.fiap.simec.dto.UsuarioDto;
import br.com.fiap.simec.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new UsuarioDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(UsuarioDto usuarioDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        usuarioService.cadastrarUsuario(usuarioDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
