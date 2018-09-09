
public class Factor extends Expression {

    // String constants
    public static final String multS = "*";
    public static final String noneS = "none";

    private Expression left, right;
    private Opcode node;

    public Factor() {
    }

    public Factor(Opcode node, Expression left, Expression right) {
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

    public Factor(Opcode node) {
        this.node = node;
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
            case mult:
                return leftL * rightL;
        }
        return 0;
    }

    enum Opcode {
        mult, none;

        public String getOp() {
            switch (this) {
                case mult:
                    return multS;
            }
            return noneS;
        }
    }
}
