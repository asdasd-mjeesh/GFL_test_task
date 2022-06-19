package dao;

import dto.ExpressionFilter;
import entity.Expression;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionDaoTest {
    ExpressionDao expressionDao = ExpressionDao.getInstance();

    @Test
    void save() {
        Expression expression = new Expression("test__", -13.37);
        var result = expressionDao.save(expression);
        System.out.println(result);
    }

    @Test
    void findById() {
        var result = expressionDao.findById(2L);
        System.out.println(result.orElse(null));
    }

    @Test
    void findAllTest() {
        List<Expression> expressions = expressionDao.findAll();
        System.out.println(expressions);
    }

    @Test
    void updateTest() throws Exception {
        Expression expression = expressionDao.findById(2L).orElseThrow(Exception::new);
        expression.setValue("updated value");
        boolean result = expressionDao.update(expression);
        assertTrue(result);
    }

    @Test
    void deleteTest() {
        boolean result = expressionDao.deleteById(5L);
        assertTrue(result);
    }

    @Test
    void findAllByFilterTest() {
        List<Expression> expressions = expressionDao.findAllByFilter(new ExpressionFilter(
                null, 0.5, -13.37, 10, 0));
        System.out.println(expressions);
    }
}