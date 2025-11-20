package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class MainMenu {

    private final AdminMenu adminMenu;
    private final RegisterMenu registerMenu;
    private final Scanner scanner;

    public MainMenu(AdminMenu adminMenu, RegisterMenu registerMenu) {
        this.adminMenu = adminMenu;
        this.registerMenu = registerMenu;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            System.out.println("\nWelcome to AcademiaDev");
            System.out.println("1. Admin Access");
            System.out.println("2. Student Access");
            System.out.println("3. Register");
            System.out.println("0. Exit");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    //pedir email se for de adm entra
                    adminMenu.show();
                    break;
                case "2":
                    //pedir email se for de estudante entra
                    System.out.println("Student Menu is under construction.");
                    break;
                case "3":
                    registerMenu.show();
                    break;
                case "0":
                    System.out.println("See you next time!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}