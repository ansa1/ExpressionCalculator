
public class Logical extends Expression {

    // String constants
    public static final String andS = "and";
    public static final String orS = "or";
    public static final String xorS = "xor";
    public static final String noneS = "none";

    private Expression left, right;
    private Opcode node;

    public Logical() {
    }

    public Logical(Opcode node, Expression left, Expression right) {
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
            case and:
                return leftL & rightL;
            case or:
                return leftL | rightL;
            case xor:
                return leftL ^ rightL;
        }
        return 0;
    }

    enum Opcode {
        and, or, xor, none;
        public String getOp() {
            switch (this) {
                case and:
                    return andS;
                case or:
                    return orS;
                case xor:
                    return xorS;
            }
            return noneS;
        }
    }
}
