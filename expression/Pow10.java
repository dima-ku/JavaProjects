package expression;

public class Pow10 extends UnaryOperation{
    public Pow10(ExpressionWithPriority expression) {
        super(expression, "pow10");
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

    @Override
    public int calculate(int a) {
        int result = 1;
        for (int i = 0; i < a; i++) {
            result *= 10;
        }
        return result;
    }

    @Override
    public double calculate(double a) {
        throw new IllegalArgumentException("Pow10 can't operate with non-integer values " + a);
    }


}
