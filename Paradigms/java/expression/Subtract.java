package expression;

import expression.exceptions.DBZException;
import expression.generic.NumberOperation;

public final class Subtract<T extends Number> extends BinOperations<T> {

    public Subtract(final CommonExpression<T> firstExp, final CommonExpression<T> secondExp) {
        super(firstExp, secondExp, " - ");
    }

    public T count(final NumberOperation<T> type, final T firstVal, final T secondVal) throws DBZException {
        return type.subtract(firstVal, secondVal);
    }
}
