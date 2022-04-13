package expression.generic;

import expression.CommonExpression;
import expression.exceptions.GenericTabulatorException;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {

        return switch (mode) {
            case "i" -> count(new IntOperations(), expression, x1, x2, y1, y2, z1, z2);
            case "d" -> count(new DoubleOperations(), expression, x1, x2, y1, y2, z1, z2);
            case "bi" -> count(new BigIntOperations(), expression, x1, x2, y1, y2, z1, z2);
            default -> throw new GenericTabulatorException("Wrong chosen mode for evaluating");
        };
    }


    private <T extends Number> Object[][][] count(NumberOperation<T> type, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        final Object[][][] resultTable = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];

        CommonExpression<T> parsedExpression = new ExpressionParser<T>().parse(expression);

        for (int i = 0; i < resultTable.length; i++) {
            for (int j = 0; j < resultTable[0].length; j++) {
                for (int k = 0; k < resultTable[0][0].length; k++) {
                    try {
                        resultTable[i][j][k] = parsedExpression.evaluate(type, x1 + i, y1 + j, z1 + k);
                    } catch (Exception e) {
                        resultTable[i][j][k] = null;
                    }
                }
            }
        }
        return resultTable;
    }
}
