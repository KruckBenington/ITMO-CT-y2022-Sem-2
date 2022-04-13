package expression.parser;

import expression.exceptions.ParserException;
import expression.exceptions.WIAException;

public class BaseParser {
    private CharSource source = null;
    private char ch = 0xffff;
    private static final char END = '\0';

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected BaseParser() {

    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }


    protected char getCurChar() {
        return ch;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) throws ParserException {
        if (!take(expected)) {
            throw new WIAException("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) throws ParserException {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected String getSource() {
        return source.getCharSourse();
    }

}
