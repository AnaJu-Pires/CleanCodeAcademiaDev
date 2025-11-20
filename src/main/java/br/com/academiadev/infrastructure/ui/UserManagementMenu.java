package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;
import br.com.academiadev.application.usecases.students.ListStudentsUseCase;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.BusinessException;
import br.com.academiadev.application.usecases.admins.ListAdminsUseCase;
import br.com.academiadev.application.usecases.admins.RegisterAdminUseCase;
import br.com.academiadev.application.usecases.students.ChangeStudentPlanUseCase;
import br.com.academiadev.application.usecases.students.SearchStudentUseCase;

public class UserManagementMenu {

    private final Scanner scanner;
    private final ListStudentsUseCase listStudentsUseCase;
    private final ChangeStudentPlanUseCase changeStudentPlanUseCase;
    private final SearchStudentUseCase searchStudentUseCase;
    private final RegisterAdminUseCase registerAdminUseCase;
    private final ListAdminsUseCase listAdminsUseCase;

    public UserManagementMenu(ListStudentsUseCase listStudentsUseCase, ChangeStudentPlanUseCase changeStudentPlanUseCase, SearchStudentUseCase searchStudentUseCase, RegisterAdminUseCase registerAdminUseCase, ListAdminsUseCase listAdminsUseCase) {
        this.scanner = new Scanner(System.in);
        this.listStudentsUseCase = listStudentsUseCase;
        this.changeStudentPlanUseCase = changeStudentPlanUseCase;
        this.searchStudentUseCase = searchStudentUseCase;
        this.registerAdminUseCase = registerAdminUseCase;
        this.listAdminsUseCase = listAdminsUseCase;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nUser Management");
            System.out.println("1. List Students");
            System.out.println("2. Search Student");
            System.out.println("3. Change Student Plan");
            System.out.println("4. Create New Admin");
            System.out.println("5. List Admins");
            System.out.println("0. Back");
            System.out.print("Option: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    listStudents();
                    ConsoleUtils.waitForEnter(scanner);
                    break;
                case "2":
                    searchStudent();
                    ConsoleUtils.waitForEnter(scanner);
                case "3":
                    changeStudentPlan();
                    ConsoleUtils.waitForEnter(scanner);
                    break;
                case "4":
                    registerAdmin();
                    ConsoleUtils.waitForEnter(scanner);
                    break;
                case "5":
                    listAdmins();
                    ConsoleUtils.waitForEnter(scanner);
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
        if (listStudentsUseCase.execute().isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        listStudentsUseCase.execute().forEach(student -> {
            System.out.println("Name: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Plan: " + student.getSubscriptionPlan());
            System.out.println("---------------------------");
        });
    }

    private void changeStudentPlan() {

        ConsoleUtils.printHeader("Change Student Subscription Plan");
        ConsoleUtils.printCancelMessage();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        if (ConsoleUtils.isExit(email)) return;

        System.out.print("Enter new subscription plan (BASIC, PREMIUM): ");
        String plan = scanner.nextLine();
        if (ConsoleUtils.isExit(plan)) return;

        try {
            changeStudentPlanUseCase.execute(email, SubscriptionPlan.valueOf(plan));
            System.out.println("Student plan changed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid subscription plan.");
        } catch (BusinessException e) {
            System.out.println("Error changing student plan: " + e.getMessage());
        }
    }

    private void searchStudent() {
        ConsoleUtils.printHeader("Search Student");
        ConsoleUtils.printCancelMessage();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        if (ConsoleUtils.isExit(email)) return;
        try {
            Student student = searchStudentUseCase.execute(email);
            System.out.println("Student found successfully.");
            System.out.println("Email: " + email);
            System.out.println("Name: " + student.getName());
            System.out.println("Plan: " + student.getSubscriptionPlan());

        } catch (BusinessException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }

    private void registerAdmin() {
        ConsoleUtils.printHeader("Register New Admin");
        ConsoleUtils.printCancelMessage();
        System.out.print("Enter admin name: ");
        String name = scanner.nextLine();
        if (ConsoleUtils.isExit(name)) return;

        System.out.print("Enter admin email: ");
        String email = scanner.nextLine();
        if (ConsoleUtils.isExit(email)) return;

        try {
            registerAdminUseCase.execute(name, email);
            System.out.println("Admin registered successfully.");
        } catch (BusinessException e) {
            System.out.println("Error registering admin: " + e.getMessage());
        }
    }

    private void listAdmins() {
        ConsoleUtils.printHeader("List of Admins");
        Iterable<Admin> admins = listAdminsUseCase.execute();
        int count = 0;
        for (Admin admin : admins) {
            count++;
            System.out.println("Name: " + admin.getName());
            System.out.println("Email: " + admin.getEmail());
            System.out.println("---------------------------");
        }
        System.out.println("Total Admins: " + count);
    }
}