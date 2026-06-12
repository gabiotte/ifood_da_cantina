package br.com.ifooddacantina.controller;

import br.com.ifooddacantina.dto.ItemSelecionado;
import br.com.ifooddacantina.model.Pedido;
import br.com.ifooddacantina.service.CardapioService;
import br.com.ifooddacantina.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CardapioController {

    private final CardapioService cardapioService;
    private final PedidoService pedidoService;

    public CardapioController(CardapioService cardapioService, PedidoService pedidoService) {
        this.cardapioService = cardapioService;
        this.pedidoService = pedidoService;
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

        boolean nomeInvalido = nomeAluno == null || nomeAluno.isBlank();
        boolean semItens = selecionados.isEmpty();

        if (nomeInvalido) model.addAttribute("mensagemNome", "Informe seu nome para continuar.");
        if (semItens) model.addAttribute("mensagemItens", "Selecione ao menos um item para montar seu pedido.");
        if (nomeInvalido || semItens) return "aluno";

        if (horarioRetirada == null || horarioRetirada.isBlank()) {
            model.addAttribute("mensagemSelecao", "Selecione um horário de retirada.");
            return "aluno";
        }

        if (!cardapioService.horarioValido(horarioRetirada)) {
            model.addAttribute("mensagemSelecao", "O horário de retirada deve ser entre 07:00 e 21:30.");
            return "aluno";
        }

        return "aluno";
    }

    /**
     * RF05 — Confirmar pedido
     * Dado que os dados obrigatórios foram preenchidos, quando o aluno confirma,
     * o pedido é registrado no banco e uma tela de confirmação é exibida.
     */
    @PostMapping("/confirmar-pedido")
    public String confirmarPedido(
            @RequestParam(name = "nomeAluno", required = false) String nomeAluno,
            @RequestParam(name = "horarioRetirada") String horarioRetirada,
            @RequestParam(name = "itemId", required = false) List<Long> itemIds,
            @RequestParam(name = "quantidade", required = false) List<Integer> quantidades,
            Model model
    ) {
        // Validação: nome obrigatório
        if (nomeAluno == null || nomeAluno.isBlank()) {
            return redirecionarComErro(model, "Nome do aluno é obrigatório.");
        }

        // Validação: horário obrigatório e válido
        if (horarioRetirada == null || horarioRetirada.isBlank()) {
            return redirecionarComErro(model, "Horário de retirada é obrigatório.");
        }
        if (!cardapioService.horarioValido(horarioRetirada)) {
            return redirecionarComErro(model, "O horário de retirada deve ser entre 07:00 e 21:30.");
        }

        // Validação: ao menos um item selecionado
        List<ItemSelecionado> selecionados = cardapioService.montarSelecao(itemIds, quantidades);
        if (selecionados.isEmpty()) {
            return redirecionarComErro(model, "Selecione ao menos um item para confirmar o pedido.");
        }

        BigDecimal total = cardapioService.calcularTotalSelecionado(selecionados);

        // Persiste o pedido
        Pedido pedido = pedidoService.confirmarPedido(nomeAluno, horarioRetirada, selecionados, total);

        // Monta a tela de confirmação
        model.addAttribute("pedido", pedido);
        model.addAttribute("itensDoPedido", pedidoService.buscarItensDoPedido(pedido.getId()));
        return "confirmacao";
    }

    private String redirecionarComErro(Model model, String mensagem) {
        model.addAttribute("itens", cardapioService.listarItensDisponiveis());
        model.addAttribute("mensagemSelecao", mensagem);
        return "aluno";
    }
}
