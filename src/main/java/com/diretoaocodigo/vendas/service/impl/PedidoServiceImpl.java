package com.diretoaocodigo.vendas.service.impl;

import com.diretoaocodigo.vendas.domain.entity.Pedido;
import com.diretoaocodigo.vendas.domain.enums.StatusPedido;
import com.diretoaocodigo.vendas.rest.dto.PedidoDTO;
import com.diretoaocodigo.vendas.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Override
    public Pedido save(PedidoDTO pedidoDTO) {
        

        return null;
    }

    @Override
    public Optional<Pedido> bringComplete(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateStatus(Integer id, StatusPedido statusPedido) {

    }
}
