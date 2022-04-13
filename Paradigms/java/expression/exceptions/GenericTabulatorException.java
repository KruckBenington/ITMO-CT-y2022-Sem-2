package expression.exceptions;

public class GenericTabulatorException extends RuntimeException {

    public GenericTabulatorException(String massage) {
        super(massage);
    }

    public GenericTabulatorException(String massage, Throwable cause) {
        super(massage, cause);
    }

}
