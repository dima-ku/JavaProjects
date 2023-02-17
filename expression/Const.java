package expression;

public class Const implements ExpressionWithPriority {

    private final double value;

    private boolean isInteger = false;
    public Const(int value) {
        isInteger = true;
        this.value = (int) value;
    }
    public Const(double value) {
        this.value = (double) value;
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
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

    @Override
    public String toString() {
        if (isInteger) {
            return Integer.toString((int)value);
        } else {
            return Double.toString(value);
        }
    }


    @Override
    public String toMiniString() {
        return toString();
    }

    public int hashCode() {
        return Double.hashCode(value);
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof Const) {
            return toString().equals(other.toString());
        } else {
            return false;
        }
    }

}
