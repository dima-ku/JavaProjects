package expression;

import expression.exceptions.ExpressionException;
import expression.exceptions.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        ExpressionWithPriority expression = parser.parse("1000000*x*x*x*x*x/(x-1)");
        System.out.println("x\tf");;
        for (int x = 0; x <= 10; x++) {
            try {
                System.out.println(x + "   " + expression.evaluate(x, 0, 0));
            } catch (ExpressionException e) {
                System.out.println(x + "   " + e.getMessage());
            }
        }

    }

}
