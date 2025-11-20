package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class ConsoleUtils {

    private ConsoleUtils() {}

    public static void waitForEnter(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void clearScreen() {
        for (int i = 0; i < 60; i++) {
            System.out.println();
        }
    }


    public static void printHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("    " + title.toUpperCase());
        System.out.println("========================================\n");
    }

    public static void printTitle(String title) {
        System.out.println("\n--- " + title.toUpperCase() + " ---\n");
    }

    public static void printCancelMessage() {
        System.out.println("(Type 'exit' at any time to cancel)\n");
    }

    public static boolean isExit(String input) {
        if (input != null && input.trim().equalsIgnoreCase("exit")) {
            System.out.println("\nOperation canceled.");
            return true;
        }
        return false;
    }

}