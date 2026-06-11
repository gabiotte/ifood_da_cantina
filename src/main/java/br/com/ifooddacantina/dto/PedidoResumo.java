package br.com.ifooddacantina.dto;

import java.util.List;

import br.com.ifooddacantina.model.Pedido;
import br.com.ifooddacantina.model.PedidoItem;

public record PedidoResumo(Pedido pedido, List<PedidoItem> itens) {}