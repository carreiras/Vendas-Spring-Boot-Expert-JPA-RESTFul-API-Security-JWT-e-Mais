package carreiras.com.github.vendas.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException(String message) {
        super("Senha inválida.");
    }
}
