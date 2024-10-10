package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.model.User;
import org.example.service.HabitLogService;
import org.example.service.HabitService;
import org.example.service.UserService;
import org.example.model.Habit;

public class App {
    private static final Map<String, User> users = new HashMap<>();
    private static final UserService userService = new UserService(users);
    private static final HabitService habitService = new HabitService();
    private static final HabitLogService habitLogService = new HabitLogService();

    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_NAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public static void main(String[] args) {
        users.put(ADMIN_EMAIL, userService.createAdmin(ADMIN_NAME, ADMIN_EMAIL, ADMIN_PASSWORD));
        String currentUserEmail = null;
        System.out.println("Welcome to the habit tracker!");
        while (true) {
            System.out.println("Select an available command:");
            System.out.println("1- Registration");
            System.out.println("2- Entrance");
            System.out.println("3- Exit");

            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter a name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String password = scanner.nextLine();
                        String registrationResult = userService.registerUser(name, email, password);
                        System.out.println(registrationResult);
                        break;
                    case 2:
                        System.out.print("Enter email: ");
                        email = scanner.nextLine();
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();
                        String loginResult = userService.loginUser(email, password);
                        if (loginResult.equals("Authorization successful.")) {
                            if (email.equals("admin@example.com")) {
                                currentUserEmail = email;
                                adminMenu(scanner, currentUserEmail);
                            } else {
                                currentUserEmail = email;
                                userMenu(scanner, currentUserEmail);
                            }
                        } else {
                            System.out.println(loginResult);
                        }
                        break;
                    case 3:
                        System.out.println("Exiting the application...");
                        return;
                    default:
                        System.out.println("Incorrect selection. Please try again.");
                        break;
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public static void userMenu(Scanner scanner, String email) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Create Habit");
            System.out.println("2. Edit Habit");
            System.out.println("3. Delete Habit");
            System.out.println("4. View Habits");
            System.out.println("5. Log Habit");
            System.out.println("6. Edit user data");
            System.out.println("7. Return to Main Menu");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter the habit title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter the habit description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter the habit frequency (daily/weekly): ");
                        String frequency = scanner.nextLine();
                        String createResult = habitService.createHabit(email, title, description, frequency);
                        System.out.println(createResult);
                        break;
                    case 2:
                        System.out.print("Enter the title of the habit to edit: ");
                        String oldTitle = scanner.nextLine();
                        System.out.print("Enter the new title of the habit: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Enter the new description of the habit: ");
                        String newDescription = scanner.nextLine();
                        System.out.print("Enter the new frequency of the habit (daily/weekly): ");
                        String newFrequency = scanner.nextLine();
                        String updateResult = habitService.updateHabit(email, oldTitle, newTitle, newDescription,
                                newFrequency);
                        System.out.println(updateResult);
                        break;
                    case 3:
                        System.out.print("Enter the title of the habit to delete: ");
                        title = scanner.nextLine();
                        String deleteResult = habitService.deleteHabit(email, title);
                        System.out.println(deleteResult);
                        break;
                    case 4:
                        List<Habit> habits = habitService.getHabits(email);
                        System.out.println("Your habits:");
                        for (Habit habit : habits) {
                            System.out.println("- " + habit.getTitle() + " (" + habit.getFrequent() + ")");
                        }
                        break;
                    case 5:
                        System.out.print("Enter the title of the habit to log: ");
                        title = scanner.nextLine();
                        System.out.print("Enter the date of completion (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.print("Was the habit completed? (true/false): ");
                        boolean completed = Boolean.parseBoolean(scanner.nextLine());
                        Habit habitToLog = findHabitByTitle(email, title);
                        if (habitToLog != null) {
                            String logResult = habitLogService.logHabit(habitToLog, date, completed);
                            System.out.println(logResult);
                        } else {
                            System.out.println("Habit not found.");
                        }
                        break;
                    case 6:
                        System.out.print("Enter your new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter the new email: ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Enter the new password: ");
                        String newPassword = scanner.nextLine();
                        String newResult = userService.editUser(email, newName, newEmail, newPassword);
                        System.out.println(newResult);
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Incorrect selection. Please try again.");
                        break;
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static Habit findHabitByTitle(String userEmail, String title) {
        List<Habit> habits = habitService.getHabits(userEmail);
        for (Habit habit : habits) {
            if (habit.getTitle().equals(title)) {
                return habit;
            }
        }
        return null;
    }

    public static void adminMenu(Scanner scanner, String email) {
        System.out.println("1. List of users");
        System.out.println("2. Delete user");
        System.out.println("3. Exit");
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(users.keySet());
                    break;
                case 2:
                    System.out.println("Please, enter the email of the user to be deleted");
                    String userEmail = scanner.nextLine();
                    String res = userService.deleteUser(userEmail);
                    System.out.println(res);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Incorrect selection. Please try again.");
                    break;
            }
        }
    }
}
