package br.com.fiap.simec.controller;

import br.com.fiap.simec.model.Condominio;
import br.com.fiap.simec.service.CondominioService;
import br.com.fiap.simec.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/openai")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private CondominioService condominioService;

    @GetMapping("/analisar-consumo/{id}")
    public String analisarConsumo(@PathVariable Long id) {
        Condominio condominio = condominioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Condomínio não encontrado: " + id));

        Double consumoMedioCep = condominioService.findAverageConsumptionByCep(condominio.getCep());
        if (consumoMedioCep == null) {
            return String.format("Não há dados suficientes para calcular a média de consumo para o CEP %.0f.", condominio.getCep());
        }

        String situacao = condominio.getConsumoMensal() > consumoMedioCep ? "acima" : "abaixo";

        String prompt = String.format(
                "Olá moradores do Condomínio %s, Gostaríamos de informar que o consumo de energia elétrica do nosso condomínio no último mês foi de %.2f kWh, ficando %s da média para o nosso CEP, que é de %.2f kWh. Isso é uma %s notícia. Gere um texto amigável para os moradores explicando essa situação e sugerindo dicas para reduzir o consumo.",
                condominio.getNome(),
                condominio.getConsumoMensal(),
                situacao,
                consumoMedioCep,
                situacao.equals("abaixo") ? "ótima" : "preocupante"
        );

        return openAIService.generateSummary(prompt);
    }
}
