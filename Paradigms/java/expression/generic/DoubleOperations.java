package expression.generic;

import expression.exceptions.DoubleOverflowException;

public class DoubleOperations implements NumberOperation<Double> {

    @Override
    public Double add(Double firstVal, Double secondVal) {
        return firstVal + secondVal;
    }

    @Override
    public Double multiply(Double firstVal, Double secondVal) {
        return firstVal * secondVal;
    }

    @Override
    public Double subtract(Double firstVal, Double secondVal) {
        return firstVal - secondVal;
    }

    @Override
    public Double divide(Double firstVal, Double secondVal) {
        return firstVal / secondVal;
    }

    @Override
    public Double negate(Double value) {
        return -value;
    }

    @Override
    public Double parseNumber(String number) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new DoubleOverflowException("Double overflow");
        }
    }

    @Override
    public Double convert(int number) {
        return (double) number;
    }

    @Override
    public Double max(Double firstVal, Double secondVal) {
        return Double.max(firstVal, secondVal);
    }

    @Override
    public Double min(Double firstVal, Double secondVal) {
        return Double.min(firstVal, secondVal);
    }

    @Override
    public Double count(Double value) {
        return (double) Long.bitCount(Double.doubleToLongBits(value));
    }
}
