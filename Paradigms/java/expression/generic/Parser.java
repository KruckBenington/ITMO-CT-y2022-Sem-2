package expression.generic;

import expression.CommonExpression;
import expression.exceptions.ParserException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
public interface Parser<T extends Number> {
    CommonExpression<T> parse(String expression) throws ParserException;
}
