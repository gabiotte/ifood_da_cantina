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
            @RequestParam(name = "horaRetirada", required = false) String horaRetirada,
            @RequestParam(name = "minutoRetirada", required = false) String minutoRetirada,
            Model model
    ) {
        List<ItemSelecionado> selecionados = cardapioService.montarSelecao(itemIds, quantidades);

        String horarioRetirada = null;
        if (horaRetirada != null && !horaRetirada.isBlank()
        && minutoRetirada != null && !minutoRetirada.isBlank()) {
        horarioRetirada = String.format("%02d:%02d",
                Integer.parseInt(horaRetirada),
                Integer.parseInt(minutoRetirada));
    }

        model.addAttribute("itens", cardapioService.listarItensDisponiveis());
        model.addAttribute("nomeAluno", nomeAluno);
        model.addAttribute("selecionados", selecionados);
        model.addAttribute("horarioRetirada", horarioRetirada);
        model.addAttribute("totalSelecionado", cardapioService.calcularTotalSelecionado(selecionados));

        if (selecionados.isEmpty()) {
            model.addAttribute("mensagemSelecao", "Selecione ao menos um item para montar seu pedido.");
        return "aluno";
        }

        if (horarioRetirada == null || horarioRetirada.isBlank()) {
        model.addAttribute("mensagemSelecao", "Selecione um horário de retirada.");
        return "aluno";
        }

        if (!cardapioService.horarioValido(horarioRetirada)) {
        model.addAttribute("mensagemSelecao", "O horário de retirada deve ser entre 07:00 e 21:30.");
        return "aluno";
        }
        model.addAttribute("mensagemSelecao", "Pedido salvo com sucesso para retirada às " + horarioRetirada + ".");
        return "aluno";
    
}
}
