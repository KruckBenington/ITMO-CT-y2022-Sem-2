package expression;

import expression.exceptions.IntOverflowException;
import expression.generic.NumberOperation;

import java.util.Objects;

public class Const<T extends Number> implements CommonExpression<T> {

    private final String num;

    public Const(final String num) throws IntOverflowException {
        this.num = num;


    }

    @Override
    public T evaluate(final NumberOperation<T> type, final int valueOfVar) {
        return type.parseNumber(num);
    }


    @Override
    public T evaluate(final NumberOperation<T> type, final int x, final int y, final int z) {
        return type.parseNumber(num);
    }


    @Override
    public String toString() {
        return num;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Const) {
            final Const con = (Const) obj;
            return this.num == con.num;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, getClass());
    }
}
