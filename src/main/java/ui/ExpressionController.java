package ui;

import dto.ExpressionDto;
import dto.ExpressionFilter;
import entity.Expression;
import exception.InvalidExpressionException;
import service.ExpressionService;

import java.util.List;
import java.util.Scanner;

public class ExpressionController {

    private final ExpressionService expressionService;

    public ExpressionController() {
        this.expressionService = new ExpressionService();
    }

    public static void main(String[] args) {
        new ExpressionController().run();
    }

    private void run() {
        Scanner in = new Scanner(System.in);
        String choice;

        while (true) {
            showMenu();
            choice = in.nextLine();

            try {
                if (choice.equals("1")) {
                    List<ExpressionDto> expressions = expressionService.findAll();
                    System.out.println("-------------");
                    System.out.println("All expressions:");
                    for (ExpressionDto expression : expressions) {
                        System.out.println("-> " + expression + "\n");
                    }
                } else if (choice.equals("2")) {
                    System.out.println("-------------");
                    System.out.print("Enter equals value:\t");
                    String equalsStr = in.nextLine();
                    System.out.print("Enter min value:\t");
                    Double min = in.nextDouble();
                    System.out.print("Enter max value:\t");
                    Double max = in.nextDouble();
                    System.out.print("Enter limit value:\t");
                    int limit = in.nextInt();
                    System.out.print("Enter offset value:\t");
                    int offset = in.nextInt();

                    Double equals = null;
                    try {
                        equals = Double.parseDouble(equalsStr);
                    } catch (Exception ignored) {
                        /* ignored */
                    }

                    ExpressionFilter filter = new ExpressionFilter(equals, min, max, limit, offset);
                    System.out.println(filter);
                    List<ExpressionDto> expressionsByFilter = expressionService.findByFilter(filter);

                    System.out.println("Expressions by filter:");
                    for (ExpressionDto expression : expressionsByFilter) {
                        System.out.println("-> " + expression + "\n");
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Enter expression value:\t");
                    String expression = in.nextLine();

                    ExpressionDto result = expressionService.save(expression);

                    System.out.println("Your expression: ");
                    System.out.println("-> " + result);
                } else if (choice.equals("4")) {
                    System.out.print("Enter expression id, what you want to update:\t");
                    Long id = Long.parseLong(in.nextLine());

                    ExpressionDto toUpdate = expressionService.findById(id);

                    if (toUpdate == null) {
                        System.out.println("Not exist expression with id = " + id);
                    } else {
                        System.out.println("Expression to update: ");
                        System.out.println("->" + toUpdate);

                        System.out.print("\nEnter new expression value:\t");
                        String newExpressionValue = in.nextLine();
                        Expression expression = new Expression(toUpdate.id(), newExpressionValue);

                        System.out.println(expressionService.update(expression));
                    }
                } else if (choice.equals("5")) {
                    System.out.print("Enter id of expression, what you want to delete:\t");
                    Long id = Long.parseLong(in.nextLine());
                    System.out.println(expressionService.delete(id));
                } else if (choice.equals("0")) {
                    break;
                }
            } catch (InvalidExpressionException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("-------------");
        }
    }

    private void showMenu() {
        System.out.print(
                """
                        Menu
                          1. Show all expressions
                          2. Show expressions by filter
                          3. Add new expression
                          4. Update expression
                          5. Delete expression
                          0. EXIT
                        Your choice:\t"""
        );
    }
}
