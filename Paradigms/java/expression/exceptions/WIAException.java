package expression.exceptions;

public final class WIAException extends ParserException {

    public WIAException(String massage) {
        super(massage);
    }

    public WIAException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
