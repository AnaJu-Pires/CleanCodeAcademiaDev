package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class AdminMenu {

    private final CourseMenu courseMenu;
    private final UserManagementMenu userMenu;
    private final Scanner scanner;

    public AdminMenu(CourseMenu courseMenu, UserManagementMenu userMenu) {
        this.courseMenu = courseMenu;
        this.userMenu = userMenu;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            System.out.println("\nAdmin Dashboard");
            System.out.println("1. Manage Courses");
            System.out.println("2. Manage Users");
            System.out.println("0. Back to Main Menu");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    courseMenu.showMenu();
                    break;
                case "2":
                    userMenu.showMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}