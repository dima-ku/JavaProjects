package expression.exceptions;

import expression.*;
public class CheckedLog10 extends Log10 {
    public CheckedLog10(ExpressionWithPriority expression) {
         super(expression);
    }

    @Override
    public int calculate(int a) {
        try {
            return checkedCalculate(a);
        } catch (LessThanZeroException e) {
            throw new LessThanZeroException(e.getMessage());
        }
    }

    public static int checkedCalculate(int a) {
        if (a <= 0) {
            throw new LessThanZeroException("Log can't operate with non-positive values" + a);
        }
        int result = 0;
        while (a >= 10) {
            a /= 10;
            result++;
        }
        return result;
    }

}
