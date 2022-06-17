package dao;

import dto.ExpressionFilter;
import entity.Expression;
import org.junit.jupiter.api.Test;

import java.util.List;

class ExpressionDaoTest {

    @Test
    void saveTest() {
        Expression expression = new Expression("test", -13.37);
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        var result = expressionDao.save(expression);
        System.out.println(result);
    }

    @Test
    void findByIdTest() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        var result = expressionDao.findById(1L);
        System.out.println(expressionDao.findById(1L).orElse(null));
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
        Expression expression = expressionDao.findById(1L).orElseThrow(Exception::new);
        expression.setValue("updated value");
        boolean result = expressionDao.update(expression);
        System.out.println(result);
    }

    @Test
    void deleteTest() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        boolean result = expressionDao.deleteById(1L);
        System.out.println(result);
    }

    @Test
    void findAllByFilterTest() {
        ExpressionDao expressionDao = ExpressionDao.getInstance();
        List<Expression> expressions = expressionDao.findAllByFilter(new ExpressionFilter(
                null, 0.5, null, 10, 0));

        System.out.println(expressions);
    }
}