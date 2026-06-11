package br.com.ifooddacantina.repository;

import br.com.ifooddacantina.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
