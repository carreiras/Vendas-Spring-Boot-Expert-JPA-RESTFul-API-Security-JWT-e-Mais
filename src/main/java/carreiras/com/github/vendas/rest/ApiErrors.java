package carreiras.com.github.vendas.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> listErros) {
        this.errors = listErros;
    }

    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }
}
