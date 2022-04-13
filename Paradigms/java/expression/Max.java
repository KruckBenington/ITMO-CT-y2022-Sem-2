package expression;

import expression.exceptions.EvaluatingException;
import expression.generic.NumberOperation;

public class Max<T extends Number> extends BinOperations<T> {


    public Max(CommonExpression<T> exp1, CommonExpression<T> exp2) {
        super(exp1, exp2, " max ");
    }

    @Override
    public T count(NumberOperation<T> type, T firstVal, T secondVal) throws EvaluatingException {
        return type.max(firstVal, secondVal);
    }
}
