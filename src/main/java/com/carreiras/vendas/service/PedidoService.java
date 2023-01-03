package com.carreiras.vendas.service;

import com.carreiras.vendas.rest.dto.PedidoDto;
import com.carreiras.vendas.domain.entity.Pedido;
import com.carreiras.vendas.domain.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {

    Pedido include(PedidoDto pedidoDTO);

    Optional<Pedido> bringComplete(Integer id);

    void updateStatus(Integer id, StatusPedido statusPedido);
}
