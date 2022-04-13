package expression;

import expression.exceptions.DBZException;
import expression.generic.NumberOperation;

public final class Multiply<T extends Number> extends BinOperations<T> {

    public Multiply(final CommonExpression<T> firstExp, final CommonExpression<T> secondExp) {
        super(firstExp, secondExp, " * ");
    }


    @Override
    public T count(final NumberOperation<T> type, final T firstVal, final T secondVal) throws DBZException {
        return type.multiply(firstVal, secondVal);
    }
}
