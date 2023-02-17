package expression;

public class Lcm extends BinaryOperation {
    public Lcm(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right, "lcm");
    }


    @Override
    public int calculate(int a, int b) {
        return lcm(a, b);
    }

    public static int lcm(int a, int b) {
        if (Gcd.gcd(a, b) == 0) {
            return 0;
        }
        return a / Gcd.gcd(a, b) * b;
    }
    @Override
    public double calculate(double a, double b) {
        throw new UnsupportedOperationException("Lcm can't operate with double");
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean leftDistribution() {
        return false;
    }

    @Override
    public String toMiniString() {
        StringBuilder result = new StringBuilder();
        if (left.getPriority() < getPriority())  {
            result.append("(").append(left.toMiniString()).append(")");
        } else {
            result.append(left.toMiniString());
        }

        result.append(" ").append(getOperation()).append(" ");
        if (right.getPriority() == getPriority() && right.getClass() != this.getClass()) {
            result.append("(").append(right.toMiniString()).append(")");
        } else {
            result.append(right.toMiniString());
        }
        return result.toString();
    }

    @Override
    public boolean rightDistribution() {
        return false;
    }
}
