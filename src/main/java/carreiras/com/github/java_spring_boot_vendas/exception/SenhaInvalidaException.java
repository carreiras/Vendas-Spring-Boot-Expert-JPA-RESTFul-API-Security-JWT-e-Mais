package carreiras.com.github.java_spring_boot_vendas.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException(String message) {
        super("Senha inválida.");
    }
}
