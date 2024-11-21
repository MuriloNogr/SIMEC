package br.com.fiap.simec.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CondominioDto extends RepresentationModel<CondominioDto> {

    private Long id;

    @NotBlank(message = "O nome do condomínio não pode estar vazio.")
    @Size(max = 255, message = "O nome do condomínio não pode exceder 255 caracteres.")
    private String nome;

    @NotBlank(message = "O endereço do condomínio não pode estar vazio.")
    @Size(max = 255, message = "O endereço do condomínio não pode exceder 255 caracteres.")
    private String endereco;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres")
    private String cep;

    @Positive(message = "A quantidade de apartamentos é obrigatória")
    @Size(min = 1, message = "A quantidade de apartamentos deve ser maior que 0")
    private int qtd_apartamentos;

    @NotBlank(message = "O nome do sindico é obrigatório")
    @Size(max = 255, message = "O nome do sindico não pode exceder 255 caracteres.")
    private String nome_sindico;

    @NotBlank(message = "O telefone do sindico é obrigatório")
    @Size(min = 10, max = 15, message = "O telefone do sindico deve ter entre 10 e 15 caracteres.")
    private String telefone_sindico;

    @NotBlank(message = "O email do sindico é obrigatório")
    @Size(max = 255, message = "O email do sindico não pode exceder 255 caracteres.")
    private String email_sindico;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O consumo mensal é obrigatório")
    @Column(name = "CONSUMOMENSAL")
    private Double consumoMensal;


}
