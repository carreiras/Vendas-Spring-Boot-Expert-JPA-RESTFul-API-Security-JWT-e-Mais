package carreiras.com.github.vendas.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import carreiras.com.github.vendas.domain.entity.ItemPedido;
import carreiras.com.github.vendas.domain.entity.Pedido;
import carreiras.com.github.vendas.domain.enums.StatusPedido;
import carreiras.com.github.vendas.rest.dto.AtualizacaoStatusPedidoDto;
import carreiras.com.github.vendas.rest.dto.InformacaoItemPedidoDto;
import carreiras.com.github.vendas.rest.dto.InformacaoPedidoDto;
import carreiras.com.github.vendas.rest.dto.PedidoDto;
import carreiras.com.github.vendas.service.PedidoService;

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
    @ApiOperation(value = "Salva um novo cliente.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido criado com sucesso."),
            @ApiResponse(code = 400, message = "Erro(s) de validação.")
    })
    public Integer save(@RequestBody @Valid PedidoDto pedidoDTO) {
        Pedido pedido = pedidoService.include(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna um pedido completo.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exibe pedido completo."),
            @ApiResponse(code = 404, message = "Pedido não encontrado")
    })
    public InformacaoPedidoDto bringComplete(@PathVariable Integer id) {
        return pedidoService.bringComplete(id)
                .map(pedido -> builderInformacaoPedidoDTO(pedido))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualiza status de um pedido.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "NO_CONTENT - Status de pedido atualizado"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Pedido não encontrado")
    })
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDto atualizacaoStatusPedidoDTO) {
        String novoStatus = atualizacaoStatusPedidoDTO.getNovoStatus();
        pedidoService.updateStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacaoPedidoDto builderInformacaoPedidoDTO(Pedido pedido) {
        return InformacaoPedidoDto.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(builderInformacaoItemPedidoDTO(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDto> builderInformacaoItemPedidoDTO(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens))
            return Collections.emptyList();
        return itens.stream()
                .map(item -> InformacaoItemPedidoDto.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                ).collect(Collectors.toList());
    }
}
