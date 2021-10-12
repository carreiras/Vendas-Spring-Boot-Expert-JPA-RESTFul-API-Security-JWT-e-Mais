package com.diretoaocodigo.vendas.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 100)
    @NotEmpty(message = "Campo NOME é obrigatório.")
    private String nome;

    @Column(name = "cpf", length = 11)
    @CPF(message = "Informe um CPF válido.")
    @NotEmpty(message = "Campo CPF é obrigatório.")
    private String cpf;
}
