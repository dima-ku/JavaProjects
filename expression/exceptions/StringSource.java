package expression.exceptions;

public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public void back(int shift) {
        pos -= shift;
    }
    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public ParsingException error(final String message) {
        return new ParsingException(pos + ": " + message);
    }
}
