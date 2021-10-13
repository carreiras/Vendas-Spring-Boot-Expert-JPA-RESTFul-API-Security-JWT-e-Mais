package com.diretoaocodigo.vendas.rest.controller;

import com.diretoaocodigo.vendas.domain.entity.Cliente;
import com.diretoaocodigo.vendas.domain.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Clientes")
@RequestMapping("/api/clientes")
public class ClienteController {

    private static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado.";

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED - Cliente criado com sucesso"),
            @ApiResponse(code = 400, message = "BAD_REQUEST - Erro(s) de validação"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public Cliente include(@RequestBody @Valid Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Atualiza um cliente existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Cliente atualizado com sucesso"),
            @ApiResponse(code = 400, message = "BAD_REQUEST - Erro(s) de validação"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Cliente não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
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
    @ApiOperation(value = "Exclui um cliente existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "NO_CONTENT - Cliente excluído com sucesso"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Cliente não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
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
    @ApiOperation(value = "Lista todos os clientes cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Lista de clientes cadastrados"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Pesquisa um cliente por Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Cliente encontrado"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Cliente não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public Cliente findById(@PathVariable Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Filtra clientes por atributos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Lsta de Cliente(s) encontrado(s)"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public List<Cliente> filter(Cliente cliente) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example clienteFiltered = Example.of(cliente, exampleMatcher);
        return clienteRepository.findAll(clienteFiltered);
    }
}
