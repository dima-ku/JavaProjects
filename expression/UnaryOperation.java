package expression;
public abstract class UnaryOperation implements ExpressionWithPriority, ToMiniString {
    protected final ExpressionWithPriority expression;
    private final String operation;

    public UnaryOperation(ExpressionWithPriority expression, String operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation + "(" + expression + ")";
    }

    public ExpressionWithPriority get() {
        return expression;
    }

    public String getOperation() {
        return operation;
    }

    public String toMiniString() {
        if (expression.getPriority() == getPriority()) {
            return operation + " " + expression.toMiniString();
        } else {
            return operation + "(" + expression.toMiniString() + ")";
        }
    }

    public abstract int calculate(int a);

    public abstract double calculate(double a);

    public int hashCode() {
        return expression.hashCode() * 239 + operation.hashCode();
    }

    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(getClass())) {
            return ((UnaryOperation) other).get().equals(get());
        } else {
            return false;
        }
    }

    public int evaluate(int x, int y, int z) {
        return calculate(expression.evaluate(x, y, z));
    }
}
