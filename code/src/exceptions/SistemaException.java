package exceptions;

public class SistemaException extends RuntimeException {
    public SistemaException(String mensagem) {
        super(mensagem);
    }

    public SistemaException(String mensagem, Throwable erro) {
        super(mensagem, erro);
    }
}
