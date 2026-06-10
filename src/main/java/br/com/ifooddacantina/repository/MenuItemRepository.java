package br.com.ifooddacantina.repository;

import br.com.ifooddacantina.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByDisponivelTrueOrderByNomeAsc();
}
