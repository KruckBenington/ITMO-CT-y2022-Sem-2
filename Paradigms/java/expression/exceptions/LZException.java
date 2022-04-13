package expression.exceptions;

public final class LZException extends ParserException {
    public LZException(String massage) {
        super(massage);
    }

    public LZException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
