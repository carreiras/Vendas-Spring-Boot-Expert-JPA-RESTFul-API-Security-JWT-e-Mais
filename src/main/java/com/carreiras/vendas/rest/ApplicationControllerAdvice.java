package com.carreiras.vendas.rest;

import com.carreiras.vendas.exception.PedidoNaoEncontradoException;
import com.carreiras.vendas.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegraNegocioException.class)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
        return new ApiErrors(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResponseStatusException.class)
    public ApiErrors handleResponseStatusException(ResponseStatusException ex) {
        return new ApiErrors(ex.getReason());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> collectErrors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(collectErrors);
    }
}
