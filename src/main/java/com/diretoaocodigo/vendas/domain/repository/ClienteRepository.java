package com.diretoaocodigo.vendas.domain.repository;

import com.diretoaocodigo.vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
