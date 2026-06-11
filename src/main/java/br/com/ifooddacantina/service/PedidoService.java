package br.com.ifooddacantina.service;

import br.com.ifooddacantina.dto.ItemSelecionado;
import br.com.ifooddacantina.model.Pedido;
import br.com.ifooddacantina.model.PedidoItem;
import br.com.ifooddacantina.repository.PedidoItemRepository;
import br.com.ifooddacantina.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         PedidoItemRepository pedidoItemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    @Transactional
    public Pedido confirmarPedido(String nomeAluno,
                                  String horarioRetirada,
                                  List<ItemSelecionado> itens,
                                  BigDecimal total) {

        Pedido pedido = pedidoRepository.save(
                new Pedido(nomeAluno, horarioRetirada, total));

        itens.forEach(item ->
                pedidoItemRepository.save(
                        new PedidoItem(pedido, item.nome(), item.quantidade(), item.subtotal())
                )
        );

        return pedido;
    }

    public List<PedidoItem> buscarItensDoPedido(Long pedidoId) {
        return pedidoItemRepository.findByPedidoId(pedidoId);
    }
}
