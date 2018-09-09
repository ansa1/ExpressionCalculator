
public class Relation extends Expression {

    // String constants
    public static final String lessS = "<";
    public static final String greaterS = ">";
    public static final String equalS = "=";
    public static final String noneS = "none";

    private Expression left, right;
    private Opcode node;

    public Relation() {
    }

    public Relation(Opcode node, Expression left, Expression right) {
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
            case less:
                if (leftL < rightL) return 1;
                break;
            case greater:
                if (leftL > rightL) return 1;
                break;
            case equal:
                if (leftL == rightL) return 1;
                break;
        }
        return 0;
    }

    enum Opcode {
        less, greater, equal, none;
        public String getOp() {
            switch (this) {
                case less:
                    return lessS;
                case greater:
                    return greaterS;
                case equal:
                    return equalS;
            }
            return noneS;
        }
    }
}
