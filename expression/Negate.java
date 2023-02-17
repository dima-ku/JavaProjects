package expression;

public class Negate extends UnaryOperation {
    public Negate(ExpressionWithPriority expression) {
        super(expression, "-");
    }
    @Override
    public int calculate(int a) {
        return -1 * a;
    }

    @Override
    public double calculate(double a) {
        return -1 * a;
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
