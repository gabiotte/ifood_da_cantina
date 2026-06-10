package br.com.ifooddacantina.service;

import br.com.ifooddacantina.model.MenuItem;
import br.com.ifooddacantina.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioService {

    private final MenuItemRepository menuItemRepository;

    public CardapioService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> listarItensDisponiveis() {
        return menuItemRepository.findByDisponivelTrueOrderByNomeAsc();
    }
}
