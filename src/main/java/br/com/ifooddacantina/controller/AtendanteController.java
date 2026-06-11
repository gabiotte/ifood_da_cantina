package br.com.ifooddacantina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.ifooddacantina.service.PedidoService;

@Controller
public class AtendanteController {

    private final PedidoService pedidoService;

    public AtendanteController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/atendente")
    public String painel(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "atendente";
    }
}