package com.diretoaocodigo.vendas.rest.controller;

import com.diretoaocodigo.vendas.domain.entity.ItemPedido;
import com.diretoaocodigo.vendas.domain.entity.Pedido;
import com.diretoaocodigo.vendas.domain.enums.StatusPedido;
import com.diretoaocodigo.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import com.diretoaocodigo.vendas.rest.dto.InformacaoItemPedidoDTO;
import com.diretoaocodigo.vendas.rest.dto.InformacaoPedidoDTO;
import com.diretoaocodigo.vendas.rest.dto.PedidoDTO;
import com.diretoaocodigo.vendas.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna um pedido completo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Pedido encontrado"),
            @ApiResponse(code = 405, message = "NOT_FOUND - Pedido não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public InformacaoPedidoDTO bringComplete(@PathVariable Integer id) {
        return pedidoService.bringComplete(id)
                .map(pedido -> builderInformacaoPedidoDTO(pedido))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO) {
        String novoStatus = atualizacaoStatusPedidoDTO.getNovoStatus();
        pedidoService.updateStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacaoPedidoDTO builderInformacaoPedidoDTO(Pedido pedido) {
        return InformacaoPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(builderInformacaoItemPedidoDTO(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> builderInformacaoItemPedidoDTO(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens))
            return Collections.emptyList();
        return itens.stream()
                .map(item -> InformacaoItemPedidoDTO.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                ).collect(Collectors.toList());
    }
}
