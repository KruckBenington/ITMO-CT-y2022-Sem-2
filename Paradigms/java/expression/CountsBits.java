package expression;

import expression.exceptions.IntOverflowException;
import expression.generic.NumberOperation;

public class CountsBits<T extends Number> extends UnaryOperation<T> {

    public CountsBits(CommonExpression<T> exp) {
        super(exp, "count ");
    }

    @Override
    protected T count(NumberOperation<T> type, T value) throws IntOverflowException {
        return type.count(value);
    }
}
