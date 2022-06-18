package dao;

import dto.ExpressionFilter;
import entity.Expression;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionDaoTest {

    @Test
    void save() {
        Expression expression = new Expression("test__", -13.37);
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        var result = expressionDao.save(expression);
        System.out.println(result);
    }

    @Test
    void findById() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        var result = expressionDao.findById(2L);
        System.out.println(result.orElse(null));
    }

    @Test
    void findAllTest() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        List<Expression> expressions = expressionDao.findAll();
        System.out.println(expressions);
    }

    @Test
    void updateTest() throws Exception {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        Expression expression = expressionDao.findById(2L).orElseThrow(Exception::new);
        expression.setValue("updated value");
        boolean result = expressionDao.update(expression);
        assertTrue(result);
    }

    @Test
    void deleteTest() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        boolean result = expressionDao.deleteById(5L);
        assertTrue(result);
    }

    @Test
    void findAllByFilterTest() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        List<Expression> expressions = expressionDao.findAllByFilter(new ExpressionFilter(
                null, 0.5, -13.37, 10, 0));

        System.out.println(expressions);
    }
}