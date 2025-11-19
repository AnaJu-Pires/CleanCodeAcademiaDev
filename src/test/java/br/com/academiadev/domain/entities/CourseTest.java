package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void shouldCreateCourseWithValidData() {
        Course course = new Course(
            "Java Clean Arch", 
            "Aprenda arquitetura limpa", 
            "Uncle Bob", 
            20, 
            DifficultyLevel.ADVANCED
        );

        assertEquals("Java Clean Arch", course.getTitle());
        assertEquals("Aprenda arquitetura limpa", course.getDescription());
        assertEquals("Uncle Bob", course.getInstructorName());
        assertEquals(20, course.getDurationHours());
        assertEquals(DifficultyLevel.ADVANCED, course.getDifficultyLevel());
        assertEquals(CourseStatus.ACTIVE, course.getStatus());
        assertTrue(course.isActive());
    }

    @Test
    void shouldThrowExceptionWhenCreatingInvalidCourse() {

        assertThrows(DomainException.class, () -> {
            new Course(null, "Desc", "Inst", 10, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("", "Desc", "Inst", 10, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("   ", "Desc", "Inst", 10, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("Java", "Desc", null, 10, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("Java", "Desc", "Instrutor", -5, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("Java", "Desc", "Instrutor", 10, null);
        });

        assertThrows(DomainException.class, () -> {
            new Course("Java", null, null, 10, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("Java", "Desc", "", 10, DifficultyLevel.BEGINNER);
        });

        assertThrows(DomainException.class, () -> {
            new Course("Java", "Desc", "   ", 10, DifficultyLevel.BEGINNER);
        });
    }

    @Test
    void shouldChangeStatusCorrectly() {
        Course course = new Course("Java", "Desc", "Inst", 10, DifficultyLevel.BEGINNER);
        
        assertTrue(course.isActive());

        course.inactivate();
        assertEquals(CourseStatus.INACTIVE, course.getStatus());
        assertFalse(course.isActive());

        course.activate();
        assertEquals(CourseStatus.ACTIVE, course.getStatus());
        assertTrue(course.isActive());
    }
}