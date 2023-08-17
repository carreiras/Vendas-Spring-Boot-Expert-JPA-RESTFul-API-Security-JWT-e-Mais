package carreiras.com.github.vendas.service;

import java.util.Optional;

import carreiras.com.github.vendas.domain.entity.Pedido;
import carreiras.com.github.vendas.domain.enums.StatusPedido;
import carreiras.com.github.vendas.rest.dto.PedidoDto;

public interface PedidoService {

    Pedido include(PedidoDto pedidoDTO);

    Optional<Pedido> bringComplete(Integer id);

    void updateStatus(Integer id, StatusPedido statusPedido);
}
