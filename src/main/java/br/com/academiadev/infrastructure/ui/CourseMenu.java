package br.com.academiadev.infrastructure.ui;

import br.com.academiadev.application.usecases.CreateCourseUseCase;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.DomainException;

import java.util.Scanner;

public class CourseMenu {

    private final CreateCourseUseCase createCourseUseCase;
    private final Scanner scanner;

    public CourseMenu(CreateCourseUseCase createCourseUseCase) {
        this.createCourseUseCase = createCourseUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n\t ------------ COURSE MENU ------------\n");
            System.out.println("1. Create New Course");
            System.out.println("2. List Courses");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createNewCourse();
                    break;
                case "2":
                    System.out.println("\n 'ListCoursesUseCase' soon.");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }
    }

    private void createNewCourse() {
        System.out.println("\n\t --- CREATING NEW COURSE ---\n");
        
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Instructor: ");
            String instructor = scanner.nextLine();

            System.out.print("Workload (hours): ");
            int hours = Integer.parseInt(scanner.nextLine());

            System.out.println("Available levels: BEGINNER, INTERMEDIATE, ADVANCED");
            System.out.print("Difficulty: ");
            String levelStr = scanner.nextLine().toUpperCase().trim();
            DifficultyLevel level = DifficultyLevel.valueOf(levelStr);

            Course course = createCourseUseCase.execute(title, description, instructor, hours, level);

            System.out.println("\nSuccess! The course '" + course.getTitle() + "' has been created.");

        } catch (DomainException e) {
            System.out.println("\nBusiness Error: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("\nError: Workload must be a valid integer.");
            
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: Invalid difficulty level. Choose between BEGINNER, INTERMEDIATE, or ADVANCED.");

        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}