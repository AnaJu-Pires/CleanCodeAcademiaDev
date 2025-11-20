package br.com.academiadev.infrastructure.ui;

import br.com.academiadev.application.usecases.CreateCourseUseCase;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.DomainException;
import br.com.academiadev.application.usecases.ListCoursesUseCase;
import br.com.academiadev.application.usecases.SearchCourseUseCase;

import java.util.Optional;
import java.util.Scanner;

public class CourseMenu {

    private final CreateCourseUseCase createCourseUseCase;
    private final ListCoursesUseCase listCoursesUseCase;
    private final SearchCourseUseCase searchCourseUseCase;
    private final Scanner scanner;

    public CourseMenu(CreateCourseUseCase createCourseUseCase, ListCoursesUseCase listCoursesUseCase, SearchCourseUseCase searchCourseUseCase) {
        this.createCourseUseCase = createCourseUseCase;
        this.listCoursesUseCase = listCoursesUseCase;
        this.searchCourseUseCase = searchCourseUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            ConsoleUtils.printHeader("Course Management Menu");
            System.out.println("1. Create New Course");
            System.out.println("2. List Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Update Course");
            System.out.println("5. Deactivate Course");
            System.out.println("6. Reactivate Course");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createNewCourse();
                    break;
                case "2":
                    listCourses();
                    ConsoleUtils.waitForEnter(scanner);
                    break;
                case "3":
                    searchCourse();
                    ConsoleUtils.waitForEnter(scanner);
                    break;
                case "4":
                    //updateCourse();
                    break;
                case "5":
                    //deactivateCourse();
                    break;
                case "6":
                    //reactivateCourse();
                    break;
                case "0":
                    ConsoleUtils.clearScreen();
                    return;
                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }
    }

    private void createNewCourse() {
        ConsoleUtils.printTitle("Creating a New Course");
        
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

    private void listCourses() {
        ConsoleUtils.printTitle("Listing Courses");
        
        Iterable<Course> courses = listCoursesUseCase.execute();

        if (!courses.iterator().hasNext()) {
            System.out.println("No courses registered yet.");
            return;
        }

        listActiveCourses(courses);
        listInactiveCourses(courses);

        System.out.println("\nEnd of course list.");
    }

    private void listActiveCourses(Iterable<Course> courses) {
        System.out.println("\n- Active Courses:");
        boolean hasActiveCourses = false;
        int countActive = 0;
        
        for (Course course : courses) {
            if (course.isActive()) {
                hasActiveCourses = true;
                countActive++;
                System.out.println("\t" + countActive + ". " + course.getTitle() + " (" + course.getDifficultyLevel() + ")");
            }
        }
        
        if (!hasActiveCourses) {
            System.out.println("\t(None)");
        }
    }

    private void listInactiveCourses(Iterable<Course> courses) {
        System.out.println("\n- Inactive Courses:");
        boolean hasInactiveCourses = false;
        int countInactive = 0;
        
        for (Course course : courses) {
            if (!course.isActive()) {
                hasInactiveCourses = true;
                countInactive++;
                System.out.println("\t" + countInactive + ". " + course.getTitle());
            }
        }
        
        if (!hasInactiveCourses) {
            System.out.println("\t(None)");
        }
    }

    private void searchCourse() {
        ConsoleUtils.printTitle("Searching for a Course");

        System.out.print("Enter course title to search: ");
        String title = scanner.nextLine();

        Optional<Course> course = searchCourseUseCase.execute(title);

        if (course.isPresent()) {
            System.out.println("Course found:");
            System.out.println("Title: " + course.get().getTitle());
            System.out.println("Description: " + course.get().getDescription());
            System.out.println("Instructor: " + course.get().getInstructorName());
            System.out.println("Workload: " + course.get().getDurationHours() + " hours");
            System.out.println("Difficulty: " + course.get().getDifficultyLevel());
            System.out.println("Active: " + course.get().isActive());
        } else {
            System.out.println("No course found with the title: " + title);
        }
    }
}