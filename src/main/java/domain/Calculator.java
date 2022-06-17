package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator {

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

//
//        Double asd = (double) (4 * -7);
//        System.out.println(asd);

        String expression = "4*-7";
        var polishParsed = parseToReversePolishNotation(expression);

        for (ExpressionComponent expressionComponent : polishParsed) {
            System.out.print(expressionComponent + " ");
        }
        System.out.println();
        Double res = calculate(polishParsed);
        System.out.println("result: " + res);
    }

    public Double calculate(List<ExpressionComponent> expression) {
        Stack<ExpressionComponent> digits = new Stack<>();

        for (ExpressionComponent current : expression) {
            if (current.isDigit()) {
                digits.push(current);
            } else {
                Double value2 = Double.parseDouble(digits.pop().value());
                Double value1 = Double.parseDouble(digits.pop().value());
                double result = 0.0;

                if (current.value().equals("+")) {
                    result = value1 + value2;
                } else if (current.value().equals("-")) {
                    result = value1 - value2;
                } else if (current.value().equals("*")) {
                    result = value1 * value2;
                } else if (current.value().equals("/")) {
                    result = value1 / value2;
                }

                digits.push(new ExpressionComponent(result));
            }
        }
        return Double.parseDouble(digits.peek().value());
    }

    public List<ExpressionComponent> parseToReversePolishNotation(String expression) {
        List<ExpressionComponent> expressionComponents = new ArrayList<>();
        Stack<ExpressionComponent> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            ExpressionComponent current = new ExpressionComponent(expression.charAt(i));

            if (current.isDigit()) {
                expressionComponents.add(current);
                if (expression.length() > i + 1 && expression.charAt(i + 1) == '.') {
                    current.append(expression.charAt(++i));
                    while (expression.length() > i + 1 && Character.isDigit(expression.charAt(i + 1))) {
                        current.append(expression.charAt(++i));
                    }
                }
            } else if (operators.empty() || current.priority() >= operators.peek().priority() || current.value().equals("(")) {
                                                            //
                    operators.push(current);
                                 //
            } else if (current.value().equals(")")) {
                while (!operators.peek().value().equals("(")) {
                    expressionComponents.add(operators.pop());
                }
                operators.pop();
            } else if (current.priority() < operators.peek().priority()) {
                if (current.value().equals("-") &&
                    (expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/')) {
                    current.append(expression.charAt(++i));
                    operators.push(current);       //
                }
                while (current.priority() < operators.peek().priority()) {
                    expressionComponents.add(operators.pop());
                }
            }
        }

        if (!operators.empty()) {
            while (!operators.empty()) {
                expressionComponents.add(operators.pop());
            }
        }
        return expressionComponents;
    }
}
