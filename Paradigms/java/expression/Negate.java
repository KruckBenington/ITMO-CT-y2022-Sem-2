package expression;

import expression.exceptions.IntOverflowException;
import expression.generic.NumberOperation;

public final class Negate<T extends Number> extends UnaryOperation<T> {

    public Negate(final CommonExpression<T> exp) {
        super(exp, "-");
    }

    @Override
    public T count(final NumberOperation<T> type, final T value) throws IntOverflowException {
        return type.negate(value);
    }

}
