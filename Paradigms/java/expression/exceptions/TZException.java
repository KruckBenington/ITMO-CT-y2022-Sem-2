package expression.exceptions;

public final class TZException extends ParserException {
    public TZException(String massage) {
        super(massage);
    }

    public TZException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
