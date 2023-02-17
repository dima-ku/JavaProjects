package expression.exceptions;

import expression.*;

public class CheckedDivide extends Divide {
    public CheckedDivide(ExpressionWithPriority left, ExpressionWithPriority right) {
        super(left, right);
    }

    @Override
    public int calculate(int a, int b){
        return checkedCalculate(a, b);
    }

    public static int checkedCalculate(int a, int b) {
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero in divide: " + a + '/' + b);
        }
        if (b == -1 && a == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow in divide: " + a + '/' + b);
        }
        return a / b;
    }
}
