package expression;

public class Variable implements ExpressionWithPriority {
    private final String variable;
    public Variable(String variable) {
        if (!variable.equals("x") && !variable.equals("y") && !variable.equals("z")) {
            throw new UnsupportedOperationException("Unsupported variable: '" + variable + "'");
        }
        this.variable = variable;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (variable) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new UnsupportedOperationException("Invalid variable");
        };
    }



    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean leftDistribution() {
        return false;
    }

    @Override
    public boolean rightDistribution() {
        return false;
    }

    @Override
    public String toString() {
        return variable;
    }
    @Override
    public String toMiniString() {
        return variable;
    }

    public int hashCode() {
        return variable.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return toString().equals(other.toString());
        } else {
            return false;
        }
    }
}
