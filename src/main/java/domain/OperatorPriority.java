package domain;

public class OperatorPriority {

    private final char value;
    private final int priority;

    public OperatorPriority(char value) {
        this.value = value;
        if (value == '(') {
            priority = 1;
        } else if (value == '-' || value == '+') {
            priority = 2;
        } else if (value == '*' || value == '/') {
            priority = 3;
        } else {
            priority = 0;
        }
    }

    public char value() {
        return value;
    }

    public int priority() {
        return priority;
    }
}
