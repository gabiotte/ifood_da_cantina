package br.com.ifooddacantina.dto;

import java.math.BigDecimal;

public record ItemSelecionado(
        Long itemId,
        String nome,
        BigDecimal precoUnitario,
        int quantidade,
        BigDecimal subtotal
) {
}
