package expression.generic;

public interface NumberOperation<T extends Number> {

    T add(T firstVal, T secondVal);

    T multiply(T firstVal, T secondVal);

    T subtract(T firstVal, T secondVal);

    T divide(T firstVal, T secondVal);

    T negate(T value);

    T parseNumber(String number);

    T convert(int number);

    T max(T firstVal, T secondVal);

    T min(T firstVal, T secondVal);

    T count(T value);
}
