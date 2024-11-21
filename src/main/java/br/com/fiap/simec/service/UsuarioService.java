package br.com.fiap.simec.service;

import br.com.fiap.simec.dto.UsuarioDto;
import br.com.fiap.simec.model.Usuario;
import br.com.fiap.simec.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Cadastrar um novo usuário
     */
    public UsuarioDto cadastrarUsuario(UsuarioDto usuarioDto) {
        // Converte o DTO em um modelo de entidade
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        // Codifica a senha
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        // Salva no banco de dados
        Usuario savedUsuario = usuarioRepository.save(usuario);
        // Converte de volta para DTO para retornar
        return modelMapper.map(savedUsuario, UsuarioDto.class);
    }

    /**
     * Busca um usuário por username ou email
     */
    public Usuario buscarPorUsernameOuEmail(String usernameOuEmail) {
        return usuarioRepository.findByUsernameOrEmail(usernameOuEmail, usernameOuEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usernameOuEmail));
    }
}
