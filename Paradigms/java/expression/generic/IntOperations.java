package expression.generic;

import expression.exceptions.DBZException;
import expression.exceptions.IntOverflowException;

public class IntOperations implements NumberOperation<Integer> {

    @Override
    public Integer add(Integer firstVal, Integer secondVal) {

        if ((firstVal >= 0 && Integer.MAX_VALUE - firstVal < secondVal) || (firstVal <= 0 && secondVal < Integer.MIN_VALUE - firstVal)) {
            throw new IntOverflowException("overflow");
        }
        return firstVal + secondVal;

    }

    @Override
    public Integer multiply(Integer firstVal, Integer secondVal) {
        if (((secondVal > 0 && Integer.MAX_VALUE / secondVal < firstVal)
                || (secondVal < 0 && Integer.MAX_VALUE / secondVal > firstVal)
                || (secondVal > 0 && Integer.MIN_VALUE / secondVal > firstVal)
                || (secondVal < -1 && Integer.MIN_VALUE / secondVal < firstVal))) {
            throw new IntOverflowException("overflow");
        }
        return firstVal * secondVal;

    }

    @Override
    public Integer subtract(Integer firstVal, Integer secondVal) {
        if ((secondVal <= 0 && Integer.MAX_VALUE + secondVal < firstVal)
                || (secondVal >= 0 && firstVal < Integer.MIN_VALUE + secondVal)) {
            throw new IntOverflowException("overflow");
        }
        return firstVal - secondVal;
    }

    @Override
    public Integer divide(Integer firstVal, Integer secondVal) {
        if (secondVal == 0 || (secondVal == -1 && firstVal == Integer.MIN_VALUE)) {
            throw new DBZException("Division by zero");
        }
        return firstVal / secondVal;
    }

    @Override
    public Integer negate(Integer value) {
        if (value == Integer.MIN_VALUE) {
            throw new IntOverflowException("overflow");
        }
        return -(1) * value;
    }


    public Integer parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IntOverflowException("Number overflow");
        }

    }

    @Override
    public Integer convert(int number) {
        return number;
    }

    @Override
    public Integer max(Integer firstVal, Integer secondVal) {
        return Integer.max(firstVal, secondVal);
    }

    @Override
    public Integer min(Integer firstVal, Integer secondVal) {
        return Integer.min(firstVal, secondVal);
    }

    @Override
    public Integer count(Integer value) {
        return Integer.bitCount(value);
    }


}
