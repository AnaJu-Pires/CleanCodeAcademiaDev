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

        Course c1 = new Course("Java 1", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Course c2 = new Course("Java 2", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Course c3 = new Course("Java 3", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Course c4 = new Course("Java 4", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);

        student.enroll(c1);
        student.enroll(c2);
        student.enroll(c3);

        assertEquals(3, student.getEnrollments().size());

        assertThrows(DomainException.class, () -> student.enroll(c4));
    }


    @Test
    void shouldThrowExceptionWhenUserInvalid() {
        assertThrows(DomainException.class, () -> new Student(null, "email@teste.com", SubscriptionPlan.BASIC));
        assertThrows(DomainException.class, () -> new Student("", "email@teste.com", SubscriptionPlan.BASIC));

        assertThrows(DomainException.class, () -> new Student("Ana", null, SubscriptionPlan.BASIC));
        assertThrows(DomainException.class, () -> new Student("Ana", "", SubscriptionPlan.BASIC));
        
        assertThrows(DomainException.class, () -> new Student("Ana", "email-invalido-sem-arroba", SubscriptionPlan.BASIC));
    }


    @Test
    void shouldThrowExceptionWhenPlanIsNull() {
        assertThrows(DomainException.class, () -> new Student("Ana", "ana@teste.com", null));
    }

    @Test
    void shouldReturnCorrectRole() {
        Student student = new Student("Ana", "ana@teste.com", SubscriptionPlan.BASIC);
        assertEquals("Student", student.getRole());
    }

    @Test
    void shouldThrowExceptionWhenEnrollingInInactiveCourse() {
        Student student = new Student("Ana", "ana@email.com", SubscriptionPlan.BASIC);
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        
        course.inactivate();

        assertThrows(DomainException.class, () -> student.enroll(course));
    }

    @Test
    void shouldThrowExceptionWhenAlreadyEnrolled() {
        Student student = new Student("Ana", "ana@email.com", SubscriptionPlan.PREMIUM); // Premium para não dar erro de limite
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);

        student.enroll(course);

        assertThrows(DomainException.class, () -> student.enroll(course));
        assertTrue(student.isEnrolledIn(course));
    }
}