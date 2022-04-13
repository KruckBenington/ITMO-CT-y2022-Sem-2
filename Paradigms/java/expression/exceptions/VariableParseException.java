package expression.exceptions;

public final class VariableParseException extends ParserException {
    public VariableParseException(String massage) {
        super(massage);
    }

    public VariableParseException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
