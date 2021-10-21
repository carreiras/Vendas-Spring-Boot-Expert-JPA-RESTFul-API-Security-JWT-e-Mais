package com.diretoaocodigo.vendas.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
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

    @NotEmpty(message = "Campo senha obrigatório.")
    private String senha;

    @Column()
    private boolean admin;
}
