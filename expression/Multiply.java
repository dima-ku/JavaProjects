package expression;

public class Multiply extends BinaryOperation {
    public Multiply(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right, "*");
    }


    @Override
    public int calculate(int a, int b) {
        return a * b;
    }

    @Override
    public double calculate(double a, double b) {
        return a * b;
    }


    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean leftDistribution() {
        return true;
    }

    @Override
    public boolean rightDistribution() {
        return true;
    }
}
