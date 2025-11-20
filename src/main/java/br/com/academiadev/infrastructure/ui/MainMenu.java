package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class MainMenu {

    private final CourseMenu courseMenu;
    private final Scanner scanner;

    public MainMenu(CourseMenu courseMenu) {
        this.courseMenu = courseMenu;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            ConsoleUtils.printTitle("Main Menu");
            System.out.println("1. Admin Menu (Manage Courses)");
            System.out.println("2. Student Menu (Enroll/View)");
            System.out.println("3. Register");
            System.out.println("0. Exit");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    courseMenu.showMenu(); 
                    break;
                case "2":
                    System.out.println("Student Menu is under construction.");
                    break;
                case "3":
                    System.out.println("Registration Menu is under construction.");
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}