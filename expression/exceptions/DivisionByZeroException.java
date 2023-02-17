package expression.exceptions;

public class DivisionByZeroException extends CalculateException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}
