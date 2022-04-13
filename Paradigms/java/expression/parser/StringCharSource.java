package expression.parser;

public class StringCharSource implements CharSource {
    private final String data;
    private int pos;


    public StringCharSource(String data) {
        this.data = data;
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public IllegalArgumentException error(String massage) {
        return new IllegalArgumentException(pos + ": " + massage);
    }

    @Override
    public String getCharSourse() {
        return data.substring(0, pos - 1) + ".." + data.substring(pos - 1, data.length());
    }

}
