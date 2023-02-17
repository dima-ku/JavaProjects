package expression.exceptions;

import expression.*;
public class CheckedAdd extends Add {
    public CheckedAdd(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right);
    }

    @Override
    public int calculate(int a, int b) {
        return checkedCalculate(a, b);
    }

    public static int checkedCalculate(int a, int b)  {
        if ((a > 0 && b > 0 && a + b <= 0) || (a < 0 && b < 0 && a + b >= 0)) {
            throw new OverflowException("Overflow add: " + a + " + " + b);
        } else {
            return a + b;
        }
    }
}
