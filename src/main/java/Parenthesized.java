public class Parenthesized extends Primary {

    // String constants
    public static final char left = '(';
    public static final char right = ')';

    private Expression expression;

    public Parenthesized(Expression expression) {
        this.expression = expression;
    }

    @Override
    String toJSON(int level) {
        return expression.toJSON(level);
    }

    @Override
    long calculate() {
        return expression.calculate();
    }

    public Expression getExpression() {
        return expression;
    }
}
