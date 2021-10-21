package com.diretoaocodigo.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredencialDTO {

    private String login;
    private String senha;
}
