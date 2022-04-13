package expression.exceptions;

public final class DBZException extends EvaluatingException {
    public DBZException(String massage) {
        super(massage);
    }

    public DBZException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
