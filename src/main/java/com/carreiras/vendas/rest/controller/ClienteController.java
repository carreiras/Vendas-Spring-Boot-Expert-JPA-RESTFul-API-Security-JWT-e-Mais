package com.carreiras.vendas.rest.controller;

import com.carreiras.vendas.domain.entity.Cliente;
import com.carreiras.vendas.domain.repository.ClienteRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Api de Clientes.")
@RequestMapping("/api/clientes")
public class ClienteController {

    private static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado.";

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Obter detalhes de um cliente.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o Id informado.")
    })
    public Cliente findById(@PathVariable @ApiParam("Id do cliente") Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Salva um novo cliente.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro(s) de validação.")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Atualiza um cliente existente.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Erro(s) de validação."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
        clienteRepository.findById(id)
                .map(clienteFound -> {
                    cliente.setId(clienteFound.getId());
                    clienteRepository.save(cliente);
                    return clienteFound;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Exclui um cliente existente.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente excluído com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    public void delete(@PathVariable Integer id) {
        clienteRepository.findById(id)
                .map(clienteFound -> {
                    clienteRepository.delete(clienteFound);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lista todos os clientes cadastrados.")
    @ApiResponses({@ApiResponse(code = 200, message = "Lista de clientes cadastrados.")})
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Filtra clientes por atributos.")
    @ApiResponses({@ApiResponse(code = 200, message = "Lista de Clientes encontrados.")})
    public List<Cliente> filter(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );
        Example example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }
}
