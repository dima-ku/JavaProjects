package expression;

public class Subtract extends BinaryOperation {
    public Subtract(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right, "-");
    }


    @Override
    public int calculate(int a, int b) {
        return a - b;
    }

    @Override
    public double calculate(double a, double b) {
        return a - b;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean leftDistribution() {
        return true;
    }

    @Override
    public boolean rightDistribution() {
        return false;
    }
}
