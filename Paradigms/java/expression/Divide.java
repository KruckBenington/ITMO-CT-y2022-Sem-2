package expression;

import expression.generic.NumberOperation;

public final class Divide<T extends Number> extends BinOperations<T> {


    public Divide(final CommonExpression<T> firstExp, final CommonExpression<T> secondExp) {
        super(firstExp, secondExp, " / ");
    }


    @Override
    public T count(final NumberOperation<T> type, final T firstVal, final T secondVal) {
        return type.divide(firstVal, secondVal);
    }
}
