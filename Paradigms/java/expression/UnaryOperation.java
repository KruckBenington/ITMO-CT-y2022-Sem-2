package expression;

import expression.exceptions.IntOverflowException;
import expression.generic.NumberOperation;

import java.util.Objects;

public abstract class UnaryOperation<T extends Number> implements CommonExpression<T> {

    private final CommonExpression<T> exp;
    private final String sign;

    public UnaryOperation(final CommonExpression<T> exp, final String sign) {
        this.exp = exp;
        this.sign = sign;
    }

    @Override
    public T evaluate(final NumberOperation<T> type, final int x) {
        return count(type, exp.evaluate(type, x));
    }

    protected abstract T count(final NumberOperation<T> type, final T value) throws IntOverflowException;

    @Override
    public T evaluate(final NumberOperation<T> type, final int x, final int y, final int z) {
        return count(type, exp.evaluate(type, x, y, z));
    }

    @Override
    public String toString() {
        return sign + "(" + exp.toString() + ")";
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof UnaryOperation) {
            final UnaryOperation unarOper = (UnaryOperation<?>) obj;
            return obj.getClass() == getClass() && unarOper.exp.equals(this.exp);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sign, exp, getClass());
    }


}
