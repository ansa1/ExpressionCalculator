
public class Parser {

    private String input;
    private int counter, length;        // for pointing the input expression

    public Parser(String input) {
        this.input = input;
        length = input.length();
    }

    /*
     Starts parsing the input expression.
     */
    public Expression parse() {
        return parseLogicalFirst();
    }

    /*
     Parses Logical Operators.
        Firstly, and.
     */
    private Expression parseLogicalFirst() {
        Expression result = parseLogicalSecond();
        while (true) {
            Logical.Opcode op = parseLogOperator(false);
            if (op != Logical.Opcode.none) {
                Expression right = parseLogicalSecond();
                result = new Logical(op, result, right);
            } else break;
        }
        return result;
    }

    /*
     Continues parsing Logical Operators.
        Now or, xor.
     */
    private Expression parseLogicalSecond() {
        Expression result = parseRelation();
        while (true) {
            Logical.Opcode op = parseLogOperator(true);
            if (op != Logical.Opcode.none) {
                Expression right = parseRelation();
                result = new Logical(op, result, right);
            } else break;
        }
        return result;
    }


    /*
     Parses Relation Operators.
     */
    private Expression parseRelation() {
        Expression result = parseTerm();
        while (true) {
            Relation.Opcode op = parseRelOperator();
            if (op != Relation.Opcode.none) {
                Expression right = parseTerm();
                result = new Relation(op, result, right);
            } else break;
        }
        return result;
    }

    /*
     Parses Term Operators.
     */
    private Expression parseTerm() {
        Expression result = parseFactor();
        while (true) {
            Term.Opcode op = parseTermOperator();
            if (op != Term.Opcode.none) {
                Expression right = parseFactor();
                result = new Term(op, result, right);
            } else break;
        }
        return result;
    }

    /*
     Parses Factor Operators.
     */
    private Expression parseFactor() {
        Expression result = parsePrimary();
        while (true) {
            Factor.Opcode op = parseFacOperator();
            if (op != Factor.Opcode.none) {
                Expression right = parsePrimary();
                result = new Factor(op, result, right);
            } else break;
        }
        return result;
    }

    /*
     Parses Primary Operators: '(', ')', integers.
     */
    private Expression parsePrimary() {
        Expression result = null;
        char c;
        try {
            c = currentChar();
            // to deal with negative numbers
            if (Character.isDigit(c) || c == Term.minusS.charAt(0)) {
                result = parseInteger();
            } else if (c == Parenthesized.left) {
                increment();
                result = parse();
                increment();            // skip ')'
            } else {
                throw new Exception("Error while parsing: " + input + "\nCorrect your input plz!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     Finds integers (considering only integers in the input expression).
     */
    private Expression parseInteger() {
        long value = 0;
        boolean isNegative = false;
        if (currentChar() == '-') {
            isNegative = true;
            increment();
        }
        while (Character.isDigit(currentChar())) {
            value = value * 10 + Character.getNumericValue(currentChar());
            increment();
        }
        if (isNegative) value = -value;
        return new Integer(value);
    }

    /*
     Increments pointer to the input expression by one.
     */
    private void increment() {
        counter++;
    }

    /**
     * Increments pointer to the input expression by a given number.
     *
     * @param i a given number to increment the pointer by.
     */
    private void increment(int i) {
        counter += i;
    }

    /*
      Returns the current char in the input expression,
      space if the input expression ends.
     */
    private char currentChar() {
        if (counter >= length) {
            return ' ';
        }
        return input.charAt(counter);
    }

    /**
     * Finds Logical Operators.
     *
     * @param flag if true deals with and, otherwise -  xor and or.
     *             This flag is used for the priority in Logical Operators:
     *             and has higher one than xor, or.
     */
    private Logical.Opcode parseLogOperator(boolean flag) {
        int size, limit;
        Logical.Opcode opcodes[];
        if (flag) opcodes = new Logical.Opcode[]{Logical.Opcode.and};
        else opcodes = new Logical.Opcode[]{Logical.Opcode.xor, Logical.Opcode.or};

        for (Logical.Opcode opcode : opcodes) {
            size = opcode.getOp().length();
            limit = counter + size;
            if (limit < length && input.substring(counter, limit).equals(opcode.getOp())) {
                increment(size);
                return opcode;
            }
        }
        return Logical.Opcode.none;
    }

    /*
     Finds Relation Operators.
     */
    private Relation.Opcode parseRelOperator() {
        int size, limit;
        Relation.Opcode opcodes[] = {Relation.Opcode.less, Relation.Opcode.greater, Relation.Opcode.equal};
        for (Relation.Opcode opcode : opcodes) {
            size = opcode.getOp().length();
            limit = counter + size;
            if (limit < length && input.substring(counter, limit).equals(opcode.getOp())) {
                increment(size);
                return opcode;
            }
        }
        return Relation.Opcode.none;
    }

    /*
     Finds Term Operators.
     */
    private Term.Opcode parseTermOperator() {
        int size, limit;
        Term.Opcode opcodes[] = {Term.Opcode.plus, Term.Opcode.minus};
        for (Term.Opcode opcode : opcodes) {
            size = opcode.getOp().length();
            limit = counter + size;
            if (limit < length && input.substring(counter, limit).equals(opcode.getOp())) {
                increment(size);
                return opcode;
            }
        }
        return Term.Opcode.none;
    }

    /*
     Finds Factor Operators.
     */
    private Factor.Opcode parseFacOperator() {
        int size, limit;
        Factor.Opcode opcodes[] = {Factor.Opcode.mult, Factor.Opcode.div};
        for (Factor.Opcode opcode : opcodes) {
            size = opcode.getOp().length();
            limit = counter + size;
            if (limit < length && input.substring(counter, limit).equals(opcode.getOp())) {
                increment(size);
                return opcode;
            }
        }
        return Factor.Opcode.none;
    }

}


