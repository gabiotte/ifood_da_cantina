package br.com.ifooddacantina.controller;

import br.com.ifooddacantina.service.CardapioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
