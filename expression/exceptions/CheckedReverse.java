package expression.exceptions;

import expression.*;
public class CheckedReverse extends Reverse {
    public CheckedReverse(ExpressionWithPriority expression) {
        super(expression);
    }

    @Override
    public int calculate(int a) {
        try {
            return checkedCalculate(a);
        } catch (CalculateException e) {
            throw new OverflowException("Overflow in reverse:" + a);
        }
    }

    public static int checkedCalculate(int a) {
        boolean negative = (a < 0);
        int start = negative ? 1 : 0;
        StringBuilder sb = new StringBuilder(Integer.toString(a).substring(start));
        try {
            return negative ? CheckedNegate.checkedCalculate(Integer.parseInt(sb.reverse().toString())) : Integer.parseInt(sb.reverse().toString());
        } catch (NumberFormatException e) {
            throw new OverflowException("Overflow in reverse: " + a);
        }
    }
}
