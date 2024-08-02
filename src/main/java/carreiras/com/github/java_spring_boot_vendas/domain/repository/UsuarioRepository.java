package carreiras.com.github.java_spring_boot_vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carreiras.com.github.java_spring_boot_vendas.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
