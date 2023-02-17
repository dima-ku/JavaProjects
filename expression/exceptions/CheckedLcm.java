package expression.exceptions;

import expression.*;
public class CheckedLcm extends Lcm {
    public CheckedLcm(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right);
    }

    @Override
    public int calculate(int a, int b) {
        try {
            return checkedCalculate(a, b);
        } catch (OverflowException e) {
            throw new OverflowException("Overflow in lcm: " + a + " lcm " + b);
        }
    }

    public static int checkedCalculate(int a, int b) {
        if (a == 0 && b == 0) {
            return 0;
        }
        int gcd = CheckedGcd.checkedCalculate(a, b);
        return CheckedMultiply.checkedCalculate(CheckedDivide.checkedCalculate(a, gcd), b);
    }
}
