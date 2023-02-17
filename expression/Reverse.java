package expression;

public class Reverse extends UnaryOperation {

    public Reverse(ExpressionWithPriority expression) {
        super(expression, "reverse");
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
        return reverse(a);
    }

    int reverse(int a) {
        boolean negative = a < 0;
        String s = Integer.toString(a);
        int start = negative ? 1 : 0;
        int result = 0;
        int deg = 1;
        for (int i = start; i < s.length(); i++) {
            result += (s.charAt(i) - '0') * deg;
            deg *= 10;
        }
        return negative ? -result : result;
    }

    @Override
    public double calculate(double a) {
        throw new IllegalArgumentException("We can't reverse double numbers");
    }
}
