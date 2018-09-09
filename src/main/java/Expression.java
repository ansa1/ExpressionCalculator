abstract class Expression {
    abstract long calculate();

    abstract String toJSON(int level);

    public String toJSONInline(int level) {
        return toJSON(level).replace("\n", "").replace("\t", "");
    }

    /**
     * Tabs required for convenient json representation.
     *
     * @param level number of tabs.
     * @return String of required tabs amount.
     */
    protected String countTabs(int level) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < level; i++) {
            s.append("\t");
        }
        return s.toString();
    }
}
