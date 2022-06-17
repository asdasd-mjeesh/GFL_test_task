package domain;

import java.util.ArrayList;
import java.util.List;
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

        String expression = "5+8-3";
        var polishParsed = parseToReversePolishNotation(expression);
        for (ExpressionComponent expressionComponent : polishParsed) {
            System.out.print(expressionComponent.value() + " ");
        }
        System.out.println();
        Double res = calculate(polishParsed);
        System.out.println("result: " + res);
    }

    public Double calculate(List<ExpressionComponent> expression) {
        Stack<ExpressionComponent> digits = new Stack<>();
        Double result = 0.0;

        for (int i = 0; i < expression.size(); i++) {
            var current = expression.get(i);
            if (current.isDigit()) {
                digits.push(current);
            } else {
                Double value1 = Double.parseDouble(digits.pop().value());
                Double value2 = Double.parseDouble(digits.pop().value());
                Double res = null;

                if (current.value().equals("+")) {
                    res = value1 + value2;
                } else if (current.value().equals("-")) {
                    res = value1 - value2;
                } else if (current.value().equals("*")) {
                    res = value1 * value2;
                } else if (current.value().equals("/")) {
                    res = value1 / value2;
                }

                digits.push(new ExpressionComponent(res));
            }
        }
        return Double.parseDouble(digits.peek().value());
    }

    public List<ExpressionComponent> parseToReversePolishNotation(String expression) {
        List<ExpressionComponent> expressionComponents = new ArrayList<>();
        Stack<ExpressionComponent> operators = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            ExpressionComponent current = new ExpressionComponent(expression.charAt(i));

            if (current.isDigit()) {
                expressionComponents.add(current);
//                result.append(current.value());
                if (expression.length() > i + 1 && expression.charAt(i + 1) == '.') {
                    current.append(expression.charAt(++i));
//                    result.append(expression.charAt(++i));
                    while (expression.length() > i + 1 && Character.isDigit(expression.charAt(i + 1))) {
//                        result.append(expression.charAt(++i));
                        current.append(expression.charAt(++i));
                    }
                }
            } else if (operators.empty() || current.priority() > operators.peek().priority() || current.value().equals("(")) {
                operators.push(current);
            } else if (current.value().equals(")")) {
                while (!operators.peek().value().equals("(")) {
//                    result.append(operators.pop().value());
                    expressionComponents.add(operators.pop());
                }
                operators.pop();
            } else if (current.priority() < operators.peek().priority()) {
                while (current.priority() < operators.peek().priority()) {
//                    result.append(operators.pop().value());
                    expressionComponents.add(operators.pop());
                }
            }
        }

        if (!operators.empty()) {
            while (!operators.empty()) {
//                result.append(operators.pop().value());
                expressionComponents.add(operators.pop());
            }
        }
//        return result.toString();

        return expressionComponents;
    }
}
