package br.com.academiadev.infrastructure.ui;
import java.util.Scanner;

import br.com.academiadev.application.usecases.students.RegisterStudentUseCase;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.DomainException;

public class RegisterMenu {

    private final RegisterStudentUseCase registerStudentUseCase;
    private final Scanner scanner;

    public RegisterMenu(RegisterStudentUseCase registerStudentUseCase) {
        this.registerStudentUseCase = registerStudentUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        ConsoleUtils.printHeader("Student Registration");
        ConsoleUtils.printCancelMessage();

        System.out.print("Name: ");
        String name = this.scanner.nextLine();
        if (ConsoleUtils.isExit(name)) return;

        System.out.print("Email: ");
        String email = this.scanner.nextLine();
        if (ConsoleUtils.isExit(email)) return;

        System.out.println("Select Subscription Plan:");
        System.out.println("1. BASIC");
        System.out.println("2. PREMIUM");
        System.out.print("Option: ");
        String planOption = this.scanner.nextLine();
        if (ConsoleUtils.isExit(planOption)) return;
        SubscriptionPlan plan;
        switch (planOption) {
            case "1":
                plan = SubscriptionPlan.BASIC;
                break;
            case "2":
                plan = SubscriptionPlan.PREMIUM;
                break;
            default:
                System.out.println("Invalid option. Registration cancelled.");
                return;
        }

        try {
            Student registeredStudent = this.registerStudentUseCase.execute(name, email, plan);
            System.out.println("\nSuccess! The student '" + registeredStudent.getName() + "' has been registered.");
        } catch (DomainException e) {
            System.out.println("\nBusiness Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
        ConsoleUtils.waitForEnter(scanner);
    }
    
}
