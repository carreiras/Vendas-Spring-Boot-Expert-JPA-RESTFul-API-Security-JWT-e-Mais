package carreiras.com.github.java_spring_boot_vendas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import carreiras.com.github.java_spring_boot_vendas.domain.entity.Cliente;
import carreiras.com.github.java_spring_boot_vendas.domain.entity.ItemPedido;
import carreiras.com.github.java_spring_boot_vendas.domain.entity.Pedido;
import carreiras.com.github.java_spring_boot_vendas.domain.entity.Produto;
import carreiras.com.github.java_spring_boot_vendas.domain.enums.StatusPedido;
import carreiras.com.github.java_spring_boot_vendas.domain.repository.ClienteRepository;
import carreiras.com.github.java_spring_boot_vendas.domain.repository.ItemPedidoRepository;
import carreiras.com.github.java_spring_boot_vendas.domain.repository.PedidoRepository;
import carreiras.com.github.java_spring_boot_vendas.domain.repository.ProdutoRepository;
import carreiras.com.github.java_spring_boot_vendas.exception.PedidoNaoEncontradoException;
import carreiras.com.github.java_spring_boot_vendas.exception.RegraNegocioException;
import carreiras.com.github.java_spring_boot_vendas.rest.dto.ItemPedidoDto;
import carreiras.com.github.java_spring_boot_vendas.rest.dto.PedidoDto;
import carreiras.com.github.java_spring_boot_vendas.service.PedidoService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public Pedido include(PedidoDto pedidoDTO) {
        Cliente cliente = findCliente(pedidoDTO);
        Pedido pedido = newPedido(pedidoDTO, cliente);
        List<ItemPedido> itensPedido = convertItemPedido(pedido, pedidoDTO.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> bringComplete(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private Cliente findCliente(PedidoDto pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
    }

    private Pedido newPedido(PedidoDto pedidoDTO, Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setStatus(StatusPedido.REALIZADO);
        return pedido;
    }

    private List<ItemPedido> convertItemPedido(Pedido pedido, List<ItemPedidoDto> itensPedidoDTO) {
        if (itensPedidoDTO.isEmpty())
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        return itensPedidoDTO.stream()
                .map(itemPedidoDTO -> {
                    Integer idProduto = itemPedidoDTO.getProduto();
                    Produto produto = produtoRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido."));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
