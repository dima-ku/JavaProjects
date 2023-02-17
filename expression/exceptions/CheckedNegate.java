package expression.exceptions;

import expression.*;

public class CheckedNegate extends Negate {
    public CheckedNegate(ExpressionWithPriority expression) {
        super(expression);
    }

    @Override
    public int calculate(int a) {
        return checkedCalculate(a);
    }

    public static int checkedCalculate(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow exception in negate: " + a);
        } else {
            return -a;
        }
    }

}
