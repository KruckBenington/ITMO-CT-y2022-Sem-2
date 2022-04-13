package expression;

import expression.exceptions.EvaluatingException;
import expression.generic.NumberOperation;

public class Min<T extends Number> extends BinOperations<T> {
    public Min(CommonExpression<T> exp1, CommonExpression<T> exp2) {
        super(exp1, exp2, " min ");
    }

    @Override
    public T count(NumberOperation<T> type, T firstVal, T secondVal) throws EvaluatingException {
        return type.min(firstVal, secondVal);
    }
}
