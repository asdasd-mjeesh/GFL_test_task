package ui;

import service.ExpressionService;

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

            if (choice.equals("1")) {
                System.out.print("Input new expression: ");
                String expression = in.nextLine();
                expressionService.save(expression);
            } else if (choice.equals("0")) {
                break;
            }
        }
    }

    private void showMenu() {
        System.out.println("Menu");
        System.out.println(" 1. Create new expression");
        System.out.print("Your choice: ");
    }
}
