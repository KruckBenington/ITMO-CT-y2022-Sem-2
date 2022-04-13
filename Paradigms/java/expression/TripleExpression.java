package expression;

import expression.exceptions.EvaluatingException;
import expression.generic.NumberOperation;

public interface TripleExpression<T extends Number> {
    T evaluate(final NumberOperation<T> type, final int x, final int y, final int z) throws EvaluatingException;
}
