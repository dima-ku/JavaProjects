package expression;

public class Divide extends BinaryOperation {
    public Divide(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right, "/");
    }


    @Override
    public int calculate(int a, int b) {
        return a / b;
    }

    @Override
    public double calculate(double a, double b) {
        return a / b;
    }

    @Override
    public int getPriority() {
        return 2;
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
