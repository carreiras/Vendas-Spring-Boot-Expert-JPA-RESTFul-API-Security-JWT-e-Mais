package carreiras.com.github.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carreiras.com.github.vendas.domain.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
