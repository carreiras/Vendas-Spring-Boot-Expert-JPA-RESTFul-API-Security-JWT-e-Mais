package com.diretoaocodigo.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

    private String login;
    private String senha;
}
