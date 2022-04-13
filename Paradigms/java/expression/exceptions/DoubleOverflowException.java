package expression.exceptions;

public final class DoubleOverflowException extends EvaluatingException {
    public DoubleOverflowException(String massage) {
        super(massage);
    }

    public DoubleOverflowException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
