package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.BusinessException;
import br.com.academiadev.domain.exceptions.EnrollmentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentTest {

    @Test
    void shouldThrowExceptionWhenCreatingEnrollmentWithNulls() {
        Student student = mock(Student.class);
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);

        assertThrows(BusinessException.class, () -> new Enrollment(null, course));
        assertThrows(BusinessException.class, () -> new Enrollment(student, null));
    }

    @Test
    void shouldHandleCourseWithZeroDuration() {
        Student student = mock(Student.class);
        Course course = mock(Course.class);
        when(course.getDurationHours()).thenReturn(0);

        Enrollment enrollment = new Enrollment(student, course);

        assertEquals(0, enrollment.getProgressPercentage());
    }

    @Test
    void shouldAccumulateWatchedHoursCorrectly() {
        Student student = mock(Student.class);
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER); 
        Enrollment enrollment = new Enrollment(student, course);

        enrollment.addWatchedHours(6);
        assertEquals(6, enrollment.getWatchedHours());
        assertEquals(60, enrollment.getProgressPercentage());

        assertThrows(EnrollmentException.class, () -> {
            enrollment.addWatchedHours(5);
        });

        assertEquals(6, enrollment.getWatchedHours());

        enrollment.addWatchedHours(4);
        assertEquals(100, enrollment.getProgressPercentage());
        assertTrue(enrollment.isCompleted());
    }

    @Test
    void shouldThrowExceptionForNegativeHours() {
        Enrollment enrollment = new Enrollment(mock(Student.class), new Course("A", "B", "C", 10, DifficultyLevel.BEGINNER));
        
        assertThrows(EnrollmentException.class, () -> {
            enrollment.addWatchedHours(-1);
        });
    }

    @Test
    void shouldVerifyGetters() {
        Student student = mock(Student.class);
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Enrollment enrollment = new Enrollment(student, course);

        assertNotNull(enrollment.getStudent());
        assertNotNull(enrollment.getCourse());
        assertNotNull(enrollment.getEnrollmentDate());
        assertFalse(enrollment.isCompleted());
    }
}