package br.com.ifooddacantina.service;

import br.com.ifooddacantina.model.MenuItem;
import br.com.ifooddacantina.repository.MenuItemRepository;
import br.com.ifooddacantina.dto.ItemSelecionado;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardapioService {

    private final MenuItemRepository menuItemRepository;

    public CardapioService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> listarItensDisponiveis() {
        return menuItemRepository.findByDisponivelTrueOrderByNomeAsc();
    }

    public List<ItemSelecionado> montarSelecao(List<Long> itemIds, List<Integer> quantidades) {
        if (itemIds == null || quantidades == null || itemIds.isEmpty()) {
            return List.of();
        }

        Map<Long, Integer> quantidadePorItemId = montarMapaDeQuantidades(itemIds, quantidades);

        return menuItemRepository.findAllById(quantidadePorItemId.keySet())
                .stream()
                .filter(MenuItem::isDisponivel)
                .filter(item -> quantidadePorItemId.getOrDefault(item.getId(), 0) > 0)
                .map(item -> {
                    int quantidade = quantidadePorItemId.get(item.getId());
                    BigDecimal subtotal = item.getPreco().multiply(BigDecimal.valueOf(quantidade));
                    return new ItemSelecionado(item.getId(), item.getNome(), item.getPreco(), quantidade, subtotal);
                })
                .toList();
    }

    public BigDecimal calcularTotalSelecionado(List<ItemSelecionado> selecionados) {
        return selecionados.stream()
                .map(ItemSelecionado::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<Long, Integer> montarMapaDeQuantidades(List<Long> itemIds, List<Integer> quantidades) {
        Map<Long, Integer> quantidadePorItemId = new HashMap<>();
        int quantidadeDePares = Math.min(itemIds.size(), quantidades.size());

        for (int i = 0; i < quantidadeDePares; i++) {
            Long itemId = itemIds.get(i);
            int quantidade = Math.max(0, quantidades.get(i));
            quantidadePorItemId.merge(itemId, quantidade, Integer::sum);
        }

        return quantidadePorItemId;
    }
}
