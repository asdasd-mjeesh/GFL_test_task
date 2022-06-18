package service;

import dto.ExpressionDto;
import dto.ExpressionFilter;
import entity.Expression;
import exception.InvalidExpressionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionServiceTest {

    ExpressionService expressionService = new ExpressionService();

    @Test
    void save() {
        String expression = "29.6+16.13*(9.5/6.8)";
        ExpressionDto savedExpression = expressionService.save(expression);
        System.out.println(savedExpression);
    }

    @Test
    void saveExceptionTest() {
        String expression = "29.6+16.13*(9.5/6.8"; // not have closed bracket
        assertThrows(InvalidExpressionException.class, ()-> expressionService.save(expression));
    }

    @Test
    void findById() {
        ExpressionDto findExpression = expressionService.findById(6L);
        System.out.println(findExpression);
    }

    @Test
    void findAll() {
        List<ExpressionDto> allExpressions = expressionService.findAll();
        for (ExpressionDto expression : allExpressions) {
            System.out.println(expression);
        }
    }

    @Test
    void findByFilter() {
        ExpressionFilter filter = new ExpressionFilter(
                null, null, 20.0, 10, 0);
        List<ExpressionDto> expressionsByFilter = expressionService.findByFilter(filter);
        for (ExpressionDto expression : expressionsByFilter) {
            System.out.println(expression);
        }
    }

    @Test
    void update() {
        Expression toUpdate = new Expression(2L, "2+15", 17.0);
        assertTrue(expressionService.update(toUpdate));
    }

    @Test
    void delete() {
        boolean result = expressionService.delete(999L);
        assertFalse(result);
    }
}