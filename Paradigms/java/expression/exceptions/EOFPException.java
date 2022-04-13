package expression.exceptions;

public final class EOFPException extends ParserException {

    public EOFPException(String massage) {
        super(massage);
    }

    public EOFPException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
