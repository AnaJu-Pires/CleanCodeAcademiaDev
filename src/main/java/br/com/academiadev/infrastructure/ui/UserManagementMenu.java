package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;
import br.com.academiadev.application.usecases.students.ListStudentsUseCase;

public class UserManagementMenu {

    private final Scanner scanner;
    private final ListStudentsUseCase listStudentsUseCase;

    public UserManagementMenu(ListStudentsUseCase listStudentsUseCase) {
        this.scanner = new Scanner(System.in);
        this.listStudentsUseCase = listStudentsUseCase;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nUser Management");
            System.out.println("1. List Students");
            System.out.println("2. Change Student Plan");
            System.out.println("3. Create New Admin");
            System.out.println("0. Back");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    listStudents();
                    ConsoleUtils.waitForEnter(scanner);
                    break;
                case "2":
                    System.out.println("Feature under construction.");
                    break;
                case "3":
                    System.out.println("Feature under construction.");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void listStudents() {
        ConsoleUtils.printHeader("List of Students");
        System.out.println("Total Students: " + listStudentsUseCase.execute().size() + "\n");
        listStudentsUseCase.execute().forEach(student -> {
            System.out.println("Name: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Plan: " + student.getSubscriptionPlan());
            System.out.println("---------------------------");
        });
    }
}