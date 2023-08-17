package carreiras.com.github.vendas.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campo login obrigatório.")
    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    @NotEmpty(message = "Campo senha obrigatório.")
    private String senha;

    @Column(name = "admin")
    private boolean admin;
}
