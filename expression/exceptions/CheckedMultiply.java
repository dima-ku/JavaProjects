package expression.exceptions;

import expression.*;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right);
    }

    @Override
    public int calculate(int a, int b) {
        return checkedCalculate(a, b);
    }

    public static int checkedCalculate(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if ((a * b) / b == a && (a * b) / a == b) {
            return a * b;
        }
        throw new OverflowException("Overflow multiply: " + a  + "*" + b);
    }
}
