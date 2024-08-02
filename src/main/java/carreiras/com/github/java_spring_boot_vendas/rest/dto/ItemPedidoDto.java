package carreiras.com.github.java_spring_boot_vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDto {

    private Integer produto;
    private Integer quantidade;
}
