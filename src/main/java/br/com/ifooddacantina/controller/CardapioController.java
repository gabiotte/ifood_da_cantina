package br.com.ifooddacantina.controller;

import br.com.ifooddacantina.dto.ItemSelecionado;
import br.com.ifooddacantina.service.CardapioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping("/")
    public String visualizarCardapio(Model model) {
        model.addAttribute("itens", cardapioService.listarItensDisponiveis());
        return "aluno";
    }

    @PostMapping("/selecionar-itens")
    public String selecionarItens(
            @RequestParam(name = "nomeAluno", required = false) String nomeAluno,
            @RequestParam(name = "itemId", required = false) List<Long> itemIds,
            @RequestParam(name = "quantidade", required = false) List<Integer> quantidades,
            Model model
    ) {
        List<ItemSelecionado> selecionados = cardapioService.montarSelecao(itemIds, quantidades);

        model.addAttribute("itens", cardapioService.listarItensDisponiveis());
        model.addAttribute("nomeAluno", nomeAluno);
        model.addAttribute("selecionados", selecionados);
        model.addAttribute("totalSelecionado", cardapioService.calcularTotalSelecionado(selecionados));

        if (selecionados.isEmpty()) {
            model.addAttribute("mensagemSelecao", "Selecione ao menos um item para montar seu pedido.");
        }

        return "aluno";
    }
}
