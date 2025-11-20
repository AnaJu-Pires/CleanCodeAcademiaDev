package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class ReportsMenu {

    private final Scanner scanner;

    public ReportsMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleUtils.printHeader("System Reports & Analytics");
            System.out.println("1. List Courses by Difficulty Level");
            System.out.println("2. List Unique Instructors");
            System.out.println("3. Group Students by Plan");
            System.out.println("4. Calculate Overall Progress Average");
            System.out.println("5. Find Most Engaged Student");
            System.out.println("6. Export Data to CSV");
            System.out.println("0. Back");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    // ListCoursesByDifficultyUseCase
                    break;
                case "2":
                    // ListUniqueInstructorsUseCase
                    break;
                case "3":
                    // GroupStudentsByPlanUseCase
                    break;
                case "4":
                    // CalculateAverageProgressUseCase
                    break;
                case "5":
                    // FindMostEngagedStudentUseCase
                    break;
                case "6":
                    // ExportDataUseCase (using GenericCsvExporter)
                    // aqui se pa é outro sub menu, nao sei tem que ver
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
            
            ConsoleUtils.waitForEnter(scanner);
        }
    }
}