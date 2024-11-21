package br.com.fiap.simec.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Getter
@Setter
@Table(name = "TB_SIMEC_CONDOMINIO")
public class Condominio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do condomínio é obrigatório")
    private String nome;

    @NotBlank(message = "O endereço do condomínio é obrigatório")
    private String endereco;

    @Positive(message = "O CEP é obrigatório")
    private double cep;

    @Positive(message = "A quantidade de apartamentos é obrigatória")
    private int qtd_apartamentos;

    @NotBlank(message = "O nome do síndico é obrigatório")
    private String nome_sindico;

    @NotBlank(message = "O telefone do síndico é obrigatório")
    private String telefone_sindico;

    @NotBlank(message = "O e-mail do síndico é obrigatório")
    private String email_sindico;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @Column(name = "CONSUMOMENSAL")
    @Positive(message = "O consumo mensal deve ser maior que zero")
    private Double consumoMensal;

}
