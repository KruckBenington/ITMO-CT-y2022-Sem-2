package expression.generic;

import expression.exceptions.BigIntOverflowException;

import java.math.BigInteger;

public class BigIntOperations implements NumberOperation<BigInteger> {

    @Override
    public BigInteger add(BigInteger firstVal, BigInteger secondVal) {
        return firstVal.add(secondVal);
    }

    @Override
    public BigInteger multiply(BigInteger firstVal, BigInteger secondVal) {
        return firstVal.multiply(secondVal);
    }

    @Override
    public BigInteger subtract(BigInteger firstVal, BigInteger secondVal) {
        return firstVal.subtract(secondVal);
    }

    @Override
    public BigInteger divide(BigInteger firstVal, BigInteger secondVal) {
        return firstVal.divide(secondVal);
    }

    @Override
    public BigInteger negate(BigInteger value) {
        return value.negate();
    }

    @Override
    public BigInteger parseNumber(String number) {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new BigIntOverflowException("BigInteger overflow");
        }
    }

    @Override
    public BigInteger convert(int number) {
        return BigInteger.valueOf(number);
    }

    @Override
    public BigInteger max(BigInteger firstVal, BigInteger secondVal) {
        return firstVal.max(secondVal);
    }

    @Override
    public BigInteger min(BigInteger firstVal, BigInteger secondVal) {
        return firstVal.min(secondVal);
    }

    @Override
    public BigInteger count(BigInteger value) {
        return BigInteger.valueOf(value.bitCount());
    }


}
