package expression.exceptions;

public final class BigIntOverflowException extends EvaluatingException {
    public BigIntOverflowException(String massage) {
        super(massage);
    }

    public BigIntOverflowException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
