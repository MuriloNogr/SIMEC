package br.com.fiap.simec.controller;

import br.com.fiap.simec.model.Condominio;
import br.com.fiap.simec.service.CondominioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/condominios")
public class CondominioThymeleafController {

    @Autowired
    private CondominioService condominioService;

    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("condominios", condominioService.findAll());
        return "condominios/listar"; // Nome do template Thymeleaf para listar os condomínios
    }

    @GetMapping("/novo")
    public String novoCondominioForm(Model model) {
        model.addAttribute("condominio", new Condominio());
        return "condominios/formulario"; // Nome do template Thymeleaf para adicionar ou editar um condomínio
    }

    @PostMapping("/salvar")
    public String salvarCondominio(@ModelAttribute Condominio condominio) {
        condominioService.save(condominio);
        return "redirect:/condominios"; // Redireciona para a página de listagem após salvar
    }

    @GetMapping("/editar/{id}")
    public String editarCondominioForm(@PathVariable Long id, Model model) {
        Condominio condominio = condominioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Condomínio não encontrado: " + id));
        model.addAttribute("condominio", condominio);
        return "condominios/formulario"; // Usa o mesmo formulário para edição
    }

    @GetMapping("/excluir/{id}")
    public String excluirCondominio(@PathVariable Long id) {
        condominioService.deleteById(id);
        return "redirect:/condominios"; // Redireciona para a página de listagem após excluir
    }

    @GetMapping("/analisar-consumo/{id}")
    public String analisarConsumo(@PathVariable Long id, Model model) {
        Condominio condominio = condominioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Condomínio não encontrado: " + id));

        Double consumoMedioCep = condominioService.findAverageConsumptionByCep(condominio.getCep());
        if (consumoMedioCep == null) {
            model.addAttribute("mensagem",
                    String.format("Não há dados suficientes para calcular a média de consumo para o CEP %.0f.", condominio.getCep()));
            return "condominios/analise-consumo"; // Página para mostrar a mensagem
        }

        String situacao = condominio.getConsumoMensal() > consumoMedioCep ? "acima" : "abaixo";
        String mensagem = String.format(
                "Olá moradores do Condomínio %s, " +
                        "Gostaríamos de informar que o consumo de energia elétrica do nosso condomínio no último mês foi de %.2f kWh, " +
                        "ficando %s da média para o nosso CEP, que é de %.2f kWh.",
                condominio.getNome(), condominio.getConsumoMensal(), situacao, consumoMedioCep);

        model.addAttribute("mensagem", mensagem);
        return "condominios/analise-consumo"; // Página para exibir a análise
    }
}
