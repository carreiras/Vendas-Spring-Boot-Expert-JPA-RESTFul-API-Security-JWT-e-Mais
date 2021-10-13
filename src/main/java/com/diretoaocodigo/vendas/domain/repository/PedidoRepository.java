package com.diretoaocodigo.vendas.domain.repository;

import com.diretoaocodigo.vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
