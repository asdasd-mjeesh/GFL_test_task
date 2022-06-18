package domain;

import org.junit.jupiter.api.Test;
import util.ValueRounder;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void calculate() {
        Calculator calculator = new Calculator();

        String expression = "4*-7";
        double result = calculator.calculate(expression);
        assertEquals(-28.0, result);
        System.out.println(result);

        expression = "4*-7*-5";
        result = calculator.calculate(expression);
        assertEquals(140, result);
        System.out.println(result);
    }

    @Test
    public void calculateExpressionWithFloatPoints() {
        Calculator calculator = new Calculator();

        Double res = 29.6+16.13*(9.5/6);
        res = ValueRounder.round(res);

        String expression = "29.6+16.13*9.5/6";
        double result = calculator.calculate(expression);

        assertEquals(res, result);
        System.out.println(result);
    }
}