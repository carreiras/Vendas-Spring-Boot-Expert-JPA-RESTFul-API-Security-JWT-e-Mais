package carreiras.com.github.vendas.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import carreiras.com.github.vendas.domain.entity.Produto;
import carreiras.com.github.vendas.domain.repository.ProdutoRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Produtos")
@RequestMapping("/api/produtos")
public class ProdutoController {

    private static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado.";

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED - Produto criado com sucesso"),
            @ApiResponse(code = 400, message = "BAD_REQUEST - Erro(s) de validação"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public Produto include(@RequestBody @Valid Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Atualiza um produto existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Produto atualizado com sucesso"),
            @ApiResponse(code = 400, message = "BAD_REQUEST - Erro(s) de validação"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Produto não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        produtoRepository.findById(id)
                .map(produtoFound -> {
                    produto.setId(produtoFound.getId());
                    produtoRepository.save(produto);
                    return produtoFound;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUTO_NAO_ENCONTRADO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Exclui um produto existente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "NO_CONTENT - Produto excluído com sucesso"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Produto não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public void delete(@PathVariable Integer id) {
        produtoRepository.findById(id)
                .map(produtoFound -> {
                    produtoRepository.delete(produtoFound);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUTO_NAO_ENCONTRADO));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Lista todos os produtos cadastrados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Lista de produtos cadastrados"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Pesquisa um produto por Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Produto encontrado"),
            @ApiResponse(code = 404, message = "NOT_FOUND - Produto não encontrado"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public Produto findById(@PathVariable Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUTO_NAO_ENCONTRADO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Filtra produtos por atributos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK - Lista de Produtos(s) encontrado(s)"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    public List<Produto> filter(Produto produto) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example produtoFiltered = Example.of(produto, exampleMatcher);
        return produtoRepository.findAll(produtoFiltered);
    }
}
