import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        UserManager userManager = new UserManager("users.dat");
        ExpenseManager expenseManager = new ExpenseManager("expenses.dat");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Expense Tracker");

        User currentUser = null;
        while (currentUser == null) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                userManager.registerUser(username, password);
            } else if (choice == 2) {
                currentUser = userManager.authenticate(username, password);
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- Expense Tracker Menu ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total by Category");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    try {
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        Expense expense = new Expense(category, date, amount);
                        expenseManager.addExpense(expense);
                        System.out.println("Expense added.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    } catch (Exception e) {
                        System.out.println("Error adding expense: " + e.getMessage());
                    }
                    break;

                case 2:
                    List<Expense> expenses = expenseManager.getExpenses();
                    if (expenses.isEmpty()) {
                        System.out.println("No expenses recorded.");
                    } else {
                        System.out.println("Expenses:");
                        for (Expense exp : expenses) {
                            System.out.println(exp);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter category to view total: ");
                    String category = scanner.nextLine();
                    double total = expenseManager.getTotalByCategory(category);
                    System.out.printf("Total for %s: $%.2f%n", category, total);
                    break;

                case 4:
                    System.out.println("Exiting Expense Tracker.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
