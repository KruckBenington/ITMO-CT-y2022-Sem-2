package expression.exceptions;

public final class IntOverflowException extends EvaluatingException {
    public IntOverflowException(String massage) {
        super(massage);
    }

    public IntOverflowException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
