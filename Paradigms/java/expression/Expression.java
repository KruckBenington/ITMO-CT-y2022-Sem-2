package expression;

import expression.exceptions.EvaluatingException;
import expression.generic.NumberOperation;

public interface Expression<T extends Number> {
    T evaluate(final NumberOperation<T> type, final int x) throws EvaluatingException;
}
