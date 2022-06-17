package domain;

public class ExpressionResolver {

    private final String mathSigns;

    public ExpressionResolver() {
        this.mathSigns = "+-*/";
    }

    public boolean resolve(String expression) {
        return resolveBrackets(expression) && resolveOperations(expression);
    }

    private boolean resolveBrackets(String expression) {
        int openBrackets = 0;
        for (int i = 0; i < expression.length(); i++) {      // O(n)
            if (expression.charAt(i) == '(') {
                openBrackets++;
            } else if(expression.charAt(i) == ')') {
                openBrackets--;
            }
        }
        return openBrackets == 0;
    }

    private boolean resolveOperations(String expression) {
        for (int i = 0; i < expression.length(); i++) {      // ~ O(n^2)
            char currentChar = expression.charAt(i);
            char previousChar = '_';
            if (i > 0) {
                previousChar = expression.charAt(i - 1);
            }

            for (int j = 0; j < mathSigns.length(); j++) {
                if (currentChar == mathSigns.charAt(j)) {
                    if (i < 1 || i > expression.length() - 1) {
                        return false;
                    }

                    if (mathSigns.contains(String.valueOf(previousChar)) && previousChar != '*' && currentChar != '-'
                        && mathSigns.contains(String.valueOf(currentChar))) {
//                        throw new InvalidExpressionException("...expression " + previousChar + currentChar + " expression...");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
