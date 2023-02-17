package expression;

public interface ExpressionWithPriority extends TripleExpression {
    int getPriority();

    boolean leftDistribution();

    boolean rightDistribution();

}
