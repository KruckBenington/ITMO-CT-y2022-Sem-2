package expression.exceptions;

public final class NoSuchVariableException extends EvaluatingException {
    public NoSuchVariableException(String massage) {
        super(massage);
    }

    public NoSuchVariableException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
