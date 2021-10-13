package com.diretoaocodigo.vendas.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", length = 100)
    @NotEmpty(message = "Campo DESCRIÇÃO é obrigatório.")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "Campo PREÇO UNITÁRIO é obrigatório.")
    private BigDecimal preco;
}
