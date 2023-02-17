package expression.exceptions;

import expression.*;
public class CheckedPow10 extends Pow10 {
    public CheckedPow10(ExpressionWithPriority expression) {
        super(expression);
    }

    @Override
    public int calculate(int a) {
        try {
            return checkedCalculate(a);
        } catch (LessThanZeroException e) {
            throw new LessThanZeroException(e.getMessage());
        } catch (OverflowException e) {
            throw new OverflowException(e.getMessage());
        }
    }

    public static int checkedCalculate(int a) {
        if (a < 0) {
            throw new LessThanZeroException("Pow10 less than zero argument: " + a);
        }
        if (a > 9) {
            throw new OverflowException("Overflow in pow10: " + a);
        }
        int result = 1;
        for (int i = 0; i < a; i++) {
            result *= 10;
        }
        return result;
    }
}
