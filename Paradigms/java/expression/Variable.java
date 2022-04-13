package expression;

import expression.exceptions.NoSuchVariableException;
import expression.generic.NumberOperation;

import java.util.Objects;

public class Variable<T extends Number> implements CommonExpression<T> {

    private final String var;

    public Variable(final String var) {
        this.var = var;
    }


    public T evaluate(final NumberOperation<T> type, final int valueOfVar) {
        return type.convert(valueOfVar);
    }


    public T evaluate(final NumberOperation<T> type, final int x, final int y, final int z) {
        switch (var) {
            case ("x"):
                return type.convert(x);
            case ("y"):
                return type.convert(y);
            case ("z"):
                return type.convert(z);
            default:
                throw new NoSuchVariableException("Variable \"" + var + "\" is not supported");
        }
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Variable) {
            final Variable variable = (Variable<?>) obj;
            return this.var.equals(variable.var);
        }

        return false;
    }


    @Override
    public int hashCode() {
        return Objects.hash(var);
    }
}
