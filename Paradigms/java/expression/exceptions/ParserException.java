package expression.exceptions;

public class ParserException extends Exception {

    public ParserException(String massage) {
        super(massage);
    }

    public ParserException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
