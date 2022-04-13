package expression.exceptions;

public class EvaluatingException extends RuntimeException {

    public EvaluatingException(String massage) {
        super(massage);
    }

    public EvaluatingException(String massage, Throwable cause) {
        super(massage, cause);
    }

}
