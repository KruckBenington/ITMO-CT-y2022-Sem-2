package expression.parser;

public interface CharSource {
    char next();

    boolean hasNext();

    IllegalArgumentException error(String massage);

    String getCharSourse();

}
