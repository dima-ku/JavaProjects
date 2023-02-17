package expression.exceptions;

import expression.*;
public class CheckedGcd extends Gcd {
    public CheckedGcd(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right);
    }

    @Override
    public int calculate(int a, int b) {
        try {
            return checkedCalculate(a, b);
        } catch (OverflowException e) {
            throw new OverflowException("Overflow in gcd: " + a + " gcd " + b);
        }
    }

    public static int checkedCalculate(int a, int b) {
        while (a != 0 && b != 0) {
            if (a % b == a) {
                b = b % a;
            } else {
                a = a % b;
            }
        }
        return (a + b) > 0 ? (a + b) : -(a + b);
    }
}
