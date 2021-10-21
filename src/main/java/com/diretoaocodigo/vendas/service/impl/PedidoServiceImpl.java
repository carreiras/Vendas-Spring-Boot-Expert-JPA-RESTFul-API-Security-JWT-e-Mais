package com.diretoaocodigo.vendas.service.impl;

import com.diretoaocodigo.vendas.domain.entity.Cliente;
import com.diretoaocodigo.vendas.domain.entity.ItemPedido;
import com.diretoaocodigo.vendas.domain.entity.Pedido;
import com.diretoaocodigo.vendas.domain.entity.Produto;
import com.diretoaocodigo.vendas.domain.enums.StatusPedido;
import com.diretoaocodigo.vendas.domain.repository.ClienteRepository;
import com.diretoaocodigo.vendas.domain.repository.ItemPedidoRepository;
import com.diretoaocodigo.vendas.domain.repository.PedidoRepository;
import com.diretoaocodigo.vendas.domain.repository.ProdutoRepository;
import com.diretoaocodigo.vendas.exception.PedidoNaoEncontradoException;
import com.diretoaocodigo.vendas.exception.RegraNegocioException;
import com.diretoaocodigo.vendas.rest.dto.ItemPedidoDTO;
import com.diretoaocodigo.vendas.rest.dto.PedidoDTO;
import com.diretoaocodigo.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Pedido include(PedidoDTO pedidoDTO) {
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

    private Cliente findCliente(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
    }

    private Pedido newPedido(PedidoDTO pedidoDTO, Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setStatus(StatusPedido.REALIZADO);
        return pedido;
    }

    private List<ItemPedido> convertItemPedido(Pedido pedido, List<ItemPedidoDTO> itensPedidoDTO) {
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
