package br.com.fiap.simec.service;

import br.com.fiap.simec.model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public CustomUserDetailsService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsernameOuEmail(usernameOrEmail);

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha())
                .roles("USER") // Ajuste conforme os papéis que deseja implementar
                .build();
    }
}
