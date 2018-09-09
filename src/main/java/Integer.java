public class Integer extends Primary {
    private long value;
    
    public Integer(long value) {
        this.value = value;
    }

    @Override
    String toJSON(int level) {
        return String.valueOf(value);
    }

    @Override
    long calculate() {
        return value;
    }

    public long getValue() {
        return value;
    }
}
