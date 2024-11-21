package br.com.fiap.simec.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    private String username;
    private String email;
    private String senha;
}
