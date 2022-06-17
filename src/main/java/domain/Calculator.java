package domain;

import java.util.Stack;

public class Calculator {

    private final String mathSigns;

    public Calculator() {
        this.mathSigns = "+-*/()";
    }

    public static void main(String[] args) {
        new Calculator().run();
    }

    private void run() {
        // "a+(b-c)*d"
        // a = 5
        // b = 6
        // c = 4
        // d = 3 ->
        //  -> a+(b-c)*d = 5+(6-4)*3 = 11
        System.out.println(parseToReversePolishNotation("5+(6-4)*3"));

        String a = "5";
        String b = "2";

        Double result = Double.parseDouble(a) / Double.parseDouble(b);
        System.out.println(result);
    }

    public Double calculate(String expression) {
        Stack<Character> digits = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);
            if (Character.isDigit(current)) {
                digits.push(current);
            } else if (mathSigns.contains(String.valueOf(current))) {

            }
        }
        return null;
    }

    public String parseToReversePolishNotation(String expression) {
        Stack<OperatorPriority> operators = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            OperatorPriority currentChar = new OperatorPriority(expression.charAt(i));
            if (Character.isDigit(currentChar.value())) {
                result.append(currentChar.value());
            } else if (operators.empty() || currentChar.priority() > operators.peek().priority() || currentChar.value() == '(') {
                operators.push(currentChar);
            } else if (currentChar.value() == ')') {
                while (operators.peek().value() != '(') {
                    result.append(operators.pop().value());
                }
                operators.pop();
            } else if (currentChar.priority() < operators.peek().priority()) {
                while (currentChar.priority() < operators.peek().priority()) {
                    result.append(operators.pop().value());
                }
            }
        }

        if (!operators.empty()) {
            while (!operators.empty()) {
                result.append(operators.pop().value());
            }
        }
        return result.toString();
    }
}
