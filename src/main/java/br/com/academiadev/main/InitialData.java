package br.com.academiadev.main;

import br.com.academiadev.application.usecases.courses.CreateCourseUseCase;
import br.com.academiadev.application.usecases.courses.InactivateCourseUseCase;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class InitialData {

    private final CreateCourseUseCase createCourseUseCase;
    private final Faker faker;
    private final Random random;
    private final InactivateCourseUseCase inactivateCourseUseCase;

    public InitialData(CreateCourseUseCase createCourseUseCase, InactivateCourseUseCase inactivateCourseUseCase) {
        this.createCourseUseCase = createCourseUseCase;
        this.faker = new Faker(new Locale("en-US"));
        this.random = new Random();
        this.inactivateCourseUseCase = inactivateCourseUseCase;
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

        System.out.println("Initial data generated successfully!");

    } catch (Exception e) {
        System.err.println("Fatal error: " + e.getMessage());
    }
}


    private DifficultyLevel getRandomDifficulty() {
        DifficultyLevel[] levels = DifficultyLevel.values();
        return levels[random.nextInt(levels.length)];
    }
}
