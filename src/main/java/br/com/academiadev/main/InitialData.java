package br.com.academiadev.main;

import br.com.academiadev.application.usecases.admins.RegisterAdminUseCase;
import br.com.academiadev.application.usecases.courses.CreateCourseUseCase;
import br.com.academiadev.application.usecases.courses.InactivateCourseUseCase;
import br.com.academiadev.application.usecases.students.RegisterStudentUseCase;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class InitialData {

    private final CreateCourseUseCase createCourseUseCase;
    private final RegisterStudentUseCase registerStudentUseCase;
    private final InactivateCourseUseCase inactivateCourseUseCase;
    private final RegisterAdminUseCase registerAdminUseCase;
    private final Faker faker;
    private final Random random;

    public InitialData(CreateCourseUseCase createCourseUseCase, InactivateCourseUseCase inactivateCourseUseCase, RegisterStudentUseCase registerStudentUseCase, RegisterAdminUseCase registerAdminUseCase) {
        this.createCourseUseCase = createCourseUseCase;
        this.inactivateCourseUseCase = inactivateCourseUseCase;
        this.registerStudentUseCase = registerStudentUseCase;
        this.registerAdminUseCase = registerAdminUseCase;
        this.faker = new Faker(new Locale("pt-BR"));
        this.random = new Random();
    }

    public void populate() {
        System.out.println("Generating initial data...");

        try {

            for (int i = 0; i < 20; i++) {
                String title = faker.educator().course() + " " + faker.programmingLanguage().name();
                String description = faker.lorem().sentence(10);
                String instructor = faker.name().fullName();
                int hours = faker.number().numberBetween(10, 120);
                DifficultyLevel level = getRandomDifficulty();

                Course created = null;
                try {
                    created = createCourseUseCase.execute(title, description, instructor, hours, level);
                } catch (Exception ignored) { }

                if (created != null && random.nextDouble() < 0.3) {
                    try {
                        inactivateCourseUseCase.execute(created.getTitle());
                    } catch (Exception ignored) {}
                }
            }

            for (int i = 0; i < 15; i++) { 
                String name = faker.name().fullName();
                String email = name.toLowerCase().replace(" ", ".") + random.nextInt(1000) + "@example.com";
                
                SubscriptionPlan plan = getRandomSubscriptionPlan();

                try {
                    registerStudentUseCase.execute(name, email, plan);
                } catch (Exception ignored) {
                }
            }

            for (int i = 0; i < 5; i++) {
                String name = faker.name().fullName();
                String email = name.toLowerCase().replace(" ", ".") + "@example.com";

                try {
                    registerAdminUseCase.execute(name, email);
                } catch (Exception ignored) {
                }
            }

            System.out.println("Initial data generated successfully! (Courses & Students)");

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        }
    }

    private DifficultyLevel getRandomDifficulty() {
        DifficultyLevel[] levels = DifficultyLevel.values();
        return levels[random.nextInt(levels.length)];
    }

    private SubscriptionPlan getRandomSubscriptionPlan() {
        SubscriptionPlan[] plans = SubscriptionPlan.values();
        return plans[random.nextInt(plans.length)];
    }
}