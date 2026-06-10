package br.com.ifooddacantina.config;

import br.com.ifooddacantina.model.MenuItem;
import br.com.ifooddacantina.repository.MenuItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MenuItemRepository menuItemRepository;

    public DataInitializer(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void run(String... args) {
        if (menuItemRepository.count() > 0) {
            return;
        }

        menuItemRepository.saveAll(List.of(
                new MenuItem("Coxinha", "Coxinha de frango com catupiry.", new BigDecimal("7.50"), true),
                new MenuItem("Pao de queijo", "Porcao com tres unidades.", new BigDecimal("6.00"), true),
                new MenuItem("Sanduiche natural", "Frango, cenoura, alface e maionese.", new BigDecimal("12.00"), true),
                new MenuItem("Suco natural", "Suco de laranja 300 ml.", new BigDecimal("8.00"), true),
                new MenuItem("Bolo de chocolate", "Fatia individual.", new BigDecimal("6.50"), false)
        ));
    }
}
