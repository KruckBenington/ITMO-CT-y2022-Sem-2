package expression;

import expression.exceptions.ParserException;
import expression.generic.DoubleOperations;
import expression.generic.ExpressionParser;

public class TestArea {

    public static void main(String[] args) throws ParserException {

        CommonExpression<Double> exp = new ExpressionParser<Double>().parse("(((z + z) - 2147483647) + (z + y))");

        System.out.println(exp.evaluate(new DoubleOperations(), -6, -5, -1));

    }
}
