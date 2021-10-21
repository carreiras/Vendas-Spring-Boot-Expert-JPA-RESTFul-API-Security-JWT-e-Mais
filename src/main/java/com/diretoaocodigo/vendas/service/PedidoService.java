package com.diretoaocodigo.vendas.service;

import com.diretoaocodigo.vendas.domain.entity.Pedido;
import com.diretoaocodigo.vendas.domain.enums.StatusPedido;
import com.diretoaocodigo.vendas.rest.dto.PedidoDto;

import java.util.Optional;

public interface PedidoService {

    Pedido include(PedidoDto pedidoDTO);

    Optional<Pedido> bringComplete(Integer id);

    void updateStatus(Integer id, StatusPedido statusPedido);
}
