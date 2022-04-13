package expression;

import expression.exceptions.IntOverflowException;
import expression.generic.NumberOperation;

public final class Add<T extends Number> extends BinOperations<T> {

    public Add(final CommonExpression<T> firstExp, final CommonExpression<T> secondExp) {
        super(firstExp, secondExp, " + ");
    }


    @Override
    public T count(final NumberOperation<T> type, final T firstVal, final T secondVal) throws IntOverflowException {
        return type.add(firstVal, secondVal);
    }
}
