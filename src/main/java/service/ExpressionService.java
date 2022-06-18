package service;

import domain.ExpressionResolver;

public class ExpressionService {

    private final ExpressionResolver expressionResolver;

    public ExpressionService() {
        this.expressionResolver = new ExpressionResolver();
    }

    public void save(String expression) {
        if (expressionResolver.resolve(expression)) {
            System.out.println(expression);
        }
    }
}
