package carreiras.com.github.java_spring_boot_vendas.service;

import java.util.Optional;

import carreiras.com.github.java_spring_boot_vendas.domain.entity.Pedido;
import carreiras.com.github.java_spring_boot_vendas.domain.enums.StatusPedido;
import carreiras.com.github.java_spring_boot_vendas.rest.dto.PedidoDto;

public interface PedidoService {

    Pedido include(PedidoDto pedidoDTO);

    Optional<Pedido> bringComplete(Integer id);

    void updateStatus(Integer id, StatusPedido statusPedido);
}
