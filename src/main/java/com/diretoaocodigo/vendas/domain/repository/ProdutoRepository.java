package com.diretoaocodigo.vendas.domain.repository;

import com.diretoaocodigo.vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
