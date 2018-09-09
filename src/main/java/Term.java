
public class Term extends Expression {

    // String constants
    public static final String plusS = "+";
    public static final String minusS = "-";
    public static final String noneS = "none";

    private Expression left = new Integer(0), right;
    private Opcode node;

    public Term() {
    }

    public Term(Opcode node, Expression left, Expression right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Expression getRight() {
        return right;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getLeft() {
        return left;
    }

    public void setNode(Opcode node) {
        this.node = node;
    }

    public Opcode getNode() {
        return node;
    }

    @Override
    String toJSON(int level) {
        return "{\n" + countTabs(++level) + "\"" + node.getOp() + "\": [\n" + countTabs(++level) +
                left.toJSON(level) + ", " + right.toJSON(level) + "\n" + countTabs(--level) + "]" + "\n" + countTabs(--level) + "}";
    }

    @Override
    long calculate() {
        long leftL = left.calculate();
        long rightL = right.calculate();
        switch (node) {
            case plus:
                return leftL + rightL;
            case minus:
                return leftL - rightL;
        }
        return 0;
    }

    enum Opcode {
        plus, minus, none;
        public String getOp() {
            switch (this) {
                case plus:
                    return plusS;
                case minus:
                    return minusS;
            }
            return noneS;
        }
    }
}
