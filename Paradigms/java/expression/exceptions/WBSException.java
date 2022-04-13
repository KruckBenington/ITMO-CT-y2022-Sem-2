package expression.exceptions;

public final class WBSException extends ParserException {
    public WBSException(String massage) {
        super(massage);
    }

    public WBSException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
