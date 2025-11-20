package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class ConsoleUtils {

    private ConsoleUtils() {}

    public static void waitForEnter(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("    " + title.toUpperCase());
        System.out.println("========================================\n");
    }

   public static void printTitle(String title) {
        System.out.println("\n--- " + title.toUpperCase() + " ---\n");
    } 
}