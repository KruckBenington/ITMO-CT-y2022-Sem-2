package expression.exceptions;

public final class NumberParseException extends ParserException {
    public NumberParseException(String massage) {
        super(massage);
    }

    public NumberParseException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
