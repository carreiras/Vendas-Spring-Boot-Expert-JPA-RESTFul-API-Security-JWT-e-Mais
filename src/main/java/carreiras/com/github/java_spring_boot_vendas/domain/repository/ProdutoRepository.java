package carreiras.com.github.java_spring_boot_vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carreiras.com.github.java_spring_boot_vendas.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
