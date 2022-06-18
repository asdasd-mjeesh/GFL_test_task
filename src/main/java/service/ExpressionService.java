package service;

import dao.ExpressionDao;
import domain.Calculator;
import domain.ExpressionResolver;
import dto.ExpressionDto;
import dto.ExpressionFilter;
import entity.Expression;
import exception.InvalidExpressionException;
import mapper.ExpressionMapper;

import java.util.List;

public class ExpressionService {

    private final ExpressionResolver expressionResolver;
    private final ExpressionDao expressionDao;
    private final ExpressionMapper expressionMapper;
    private final Calculator calculator;

    public ExpressionService() {
        this.expressionResolver = new ExpressionResolver();
        this.expressionDao = ExpressionDao.getInstance();
        this.expressionMapper = new ExpressionMapper();
        this.calculator = new Calculator();
    }

    public ExpressionDto save(String expressionString) {
        expressionString = expressionString.replace(" ", "");
        if (expressionResolver.resolve(expressionString)) {
            Double calculationResult = calculator.calculate(expressionString);
            Expression expression = new Expression(expressionString, calculationResult);
            expression = expressionDao.save(expression);

            return expressionMapper.map(expression);
        } else {
            throw new InvalidExpressionException("invalid expression");
        }
    }

    public ExpressionDto findById(Long id) {
        Expression expression = expressionDao.findById(id).orElse(new Expression());
        return expressionMapper.map(expression);
    }

    public List<ExpressionDto> findAll() {
        List<Expression> expressions = expressionDao.findAll();
        return expressionMapper.map(expressions);
    }

    public List<ExpressionDto> findByFilter(ExpressionFilter filter) {
        List<Expression> expressions = expressionDao.findAllByFilter(filter);
        return expressionMapper.map(expressions);
    }

    public boolean update(Expression expression) {
        if (!expressionResolver.resolve(expression.getValue())) {
            throw new InvalidExpressionException("invalid expression");
        }
        expression.setResult(calculator.calculate(expression.getValue()));
        return expressionDao.update(expression);
    }

    public boolean delete(Long id) {
        return expressionDao.deleteById(id);
    }
}
