package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void shouldCreateStudentWithValidData() {
        Student student = new Student("Ana", "ana@email.com", SubscriptionPlan.BASIC);
        assertEquals("Ana", student.getName());
        assertEquals("ana@email.com", student.getEmail());
        assertEquals(SubscriptionPlan.BASIC, student.getSubscriptionPlan());
    }

    @Test
    void shouldThrowExceptionWhenEnrollingMoreThanAllowed() {
        Student student = new Student("Ana", "ana@email.com", SubscriptionPlan.BASIC);
        Course c1 = new Course("Java 1", "Intro", "Prof A", 10, DifficultyLevel.BEGINNER);
        Course c2 = new Course("Java 2", "Basic", "Prof A", 10, DifficultyLevel.BEGINNER);
        Course c3 = new Course("Java 3", "Advanced", "Prof A", 10, DifficultyLevel.INTERMEDIATE);
        Course c4 = new Course("Java 4", "Master", "Prof A", 10, DifficultyLevel.ADVANCED);
        student.enroll(c1);
        student.enroll(c2);
        student.enroll(c3);
        assertThrows(DomainException.class, () -> {
            student.enroll(c4);
        });
    }
}