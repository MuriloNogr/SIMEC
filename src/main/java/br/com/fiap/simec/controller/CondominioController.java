package br.com.fiap.simec.controller;

import br.com.fiap.simec.dto.CondominioDto;
import br.com.fiap.simec.model.Condominio;
import br.com.fiap.simec.service.CondominioService;
import br.com.fiap.simec.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/condominios")
@Validated
@Tag(name = "Condominios", description = "API de gerenciamento de condominios listados no sistema")
public class CondominioController {

    @Autowired
    private CondominioService condominioService;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "Obter todos os condominios", description = "Retorna uma lista de todos os condominios.")
    public List<CondominioDto> getAllCondominios() {
        return condominioService.findAll().stream()
                .map(this::convertToDtoWithLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter um condominio por ID", description = "Retorna um único condominio baseado no ID.")
    public CondominioDto getCondominioById(@PathVariable Long id) {
        return convertToDtoWithLinks(condominioService.findById(id).orElseThrow(() -> new RuntimeException("Condominio não encontrado: " + id)));
    }

    @PostMapping
    @Operation(summary = "Salvar um condominio", description = "Cria um novo condominio")
    public CondominioDto saveCondominio(@RequestBody Condominio condominio) {
        return convertToDtoWithLinks(condominioService.save(condominio));
    }

    @GetMapping("/analisar-consumo/{id}")
    @Operation(summary = "Analisar consumo de um condominio", description = "Gera insights sobre o consumo energético do condomínio com base no CEP.")
    public String analisarConsumo(@PathVariable Long id) {
        Condominio condominio = condominioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Condomínio não encontrado: " + id));

        Double consumoMedioCep = condominioService.findAverageConsumptionByCep(condominio.getCep());
        if (consumoMedioCep == null) {
            return String.format("Não há dados suficientes para calcular a média de consumo para o CEP %.0f.", condominio.getCep());
        }

        String situacao = condominio.getConsumoMensal() > consumoMedioCep ? "acima" : "abaixo";

        String prompt = String.format(
                "O condomínio %s, localizado no CEP %.0f, teve um consumo mensal de %.2f kWh. A média de consumo para esse CEP é %.2f kWh. O consumo está %s da média. Gere um texto amigável para os moradores explicando isso e dando sugestões para reduzir o consumo.",
                condominio.getNome(),
                condominio.getCep(),
                condominio.getConsumoMensal(),
                consumoMedioCep,
                situacao
        );

        return openAIService.generateSummary(prompt);
    }

    private CondominioDto convertToDtoWithLinks(Condominio condominio) {
        CondominioDto condominioDto = modelMapper.map(condominio, CondominioDto.class);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CondominioController.class).getCondominioById(condominio.getId())).withSelfRel();
        condominioDto.add(selfLink);

        return condominioDto;
    }
}
