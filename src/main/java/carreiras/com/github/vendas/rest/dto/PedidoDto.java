package carreiras.com.github.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import carreiras.com.github.vendas.validation.NotEmptyList;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    @NotNull(message = "Informe o código do cliente.")
    private Integer cliente;

    @NotNull(message = "Campo total do pedido é obrigatório.")
    private BigDecimal total;

    @NotEmptyList(message = "Pedido não pode ser realizado sem itens.")
    private List<ItemPedidoDto> itens;
}
