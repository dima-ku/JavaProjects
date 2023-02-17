package expression;

public class Log10 extends UnaryOperation {
    public Log10(ExpressionWithPriority expression) {
        super(expression, "log10");
    }

    @Override
    public int calculate(int a) {
        int result = 0;
        while (a >= 10) {
            a /= 10;
            result++;
        }
        return result;
    }

    @Override
    public double calculate(double a) {
        throw new IllegalArgumentException("log10 can operate only with int values");
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean leftDistribution() {
        return false;
    }

    @Override
    public boolean rightDistribution() {
        return false;
    }
}
