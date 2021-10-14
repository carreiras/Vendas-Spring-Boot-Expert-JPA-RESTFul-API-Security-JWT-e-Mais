package com.diretoaocodigo.vendas.rest.controller;

import com.diretoaocodigo.vendas.domain.entity.Pedido;
import com.diretoaocodigo.vendas.rest.dto.PedidoDTO;
import com.diretoaocodigo.vendas.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "Produtos")
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria um novo pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED - Pedido criado com sucesso"),
            @ApiResponse(code = 400, message = "BAD_REQUEST - Erro(s) de validação"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public Integer include(@RequestBody @Valid PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.include(pedidoDTO);
        return pedido.getId();
    }


}
