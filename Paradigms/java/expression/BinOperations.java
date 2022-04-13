package expression;

import expression.exceptions.EvaluatingException;
import expression.generic.NumberOperation;

import java.util.Objects;

public abstract class BinOperations<T extends Number> implements CommonExpression<T> {

    private final CommonExpression<T> exp1;
    private final CommonExpression<T> exp2;
    private final String operation;

    public BinOperations(final CommonExpression<T> exp1, final CommonExpression<T> exp2, final String operation) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    protected CommonExpression<T> getExp1() {
        return exp1;
    }

    protected CommonExpression<T> getExp2() {
        return exp2;
    }


    @Override
    public T evaluate(final NumberOperation<T> type, final int valueOfVar) throws EvaluatingException {
        return count(type, getExp1().evaluate(type, valueOfVar), getExp2().evaluate(type, valueOfVar));
    }


    abstract public T count(final NumberOperation<T> type, final T firstVal, final T secondVal) throws EvaluatingException;

    @Override
    public T evaluate(final NumberOperation<T> type, final int x, final int y, final int z) throws EvaluatingException {
        return count(type, getExp1().evaluate(type, x, y, z), getExp2().evaluate(type, x, y, z));
    }


    @Override
    public String toString() {
        return "(" + getExp1().toString() + operation + getExp2().toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExp1(), getExp2(), getClass());
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BinOperations) {
            final BinOperations addition = (BinOperations<?>) obj;
            return obj.getClass() == this.getClass() && this.getExp1().equals(addition.getExp1()) && this.getExp2().equals(addition.getExp2());
        }

        return false;
    }

}
