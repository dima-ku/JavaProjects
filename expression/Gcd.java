package expression;

public class Gcd extends BinaryOperation {
    public Gcd(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right, "gcd");
    }


    @Override
    public int calculate(int a, int b) {
        return gcd(a, b);
    }

    public static int gcd(int a, int b) {
        while (a != 0 && b != 0) {
            if (a % b == a) {
                b = b % a;
            } else {
                a = a % b;
            }
        }
        return (a + b) > 0 ? (a + b) : -1 * (a + b);
    }

    @Override
    public double calculate(double a, double b) {
        throw new UnsupportedOperationException("Gcd can't operate with double");
    }

    @Override
    public int getPriority() {
        return 0;
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
    public boolean leftDistribution() {
        return false;
    }

    @Override
    public boolean rightDistribution() {
        return false;
    }
}
