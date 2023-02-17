package expression;

public abstract class BinaryOperation implements ExpressionWithPriority, ToMiniString {
    protected final ExpressionWithPriority left;
    protected final ExpressionWithPriority right;
    private final String operation;

    public BinaryOperation(ExpressionWithPriority left, ExpressionWithPriority right, String operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }


    abstract public int calculate(int a, int b);

    abstract public double calculate(double a, double b);

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operation + " " + right.toString() + ")";
    }

    public ExpressionWithPriority getLeft() {
        return left;
    }

    public ExpressionWithPriority getRight() {
        return right;
    }

    public String getOperation() {
        return operation;
    }
    @Override
    public String toMiniString() {
        StringBuilder result = new StringBuilder();
        if (left.getPriority() < getPriority()) {
            result.append("(").append(left.toMiniString()).append(")");
        } else {
            result.append(left.toMiniString());
        }
        result.append(" ").append(operation).append(" ");
        if (right.getPriority() < getPriority() || (right.getPriority() == getPriority()
                                                    && (!rightDistribution() || !right.leftDistribution()))) {
            result.append("(").append(right.toMiniString()).append(")");
        } else {
            result.append(right.toMiniString());
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        return (right.hashCode() * 239 + operation.hashCode())  * 239 + left.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(getClass())) {
            return ((BinaryOperation) other).getLeft().equals(getLeft())
                    && ((BinaryOperation) other).getRight().equals(getRight());
        } else {
            return false;
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
