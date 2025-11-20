package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.BusinessException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InactivateCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private InactivateCourseUseCase useCase;

    @Test
    void shouldInactivateCourseSuccessfully() {

        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        course.activate();
        
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        useCase.execute("Java");

        assertFalse(course.isActive());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldNotChangeStatusIfAlreadyInactive() {
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        course.inactivate();
        
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(course));

        useCase.execute("Java");

        assertFalse(course.isActive()); 
        verify(courseRepository, times(0)).save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findByTitle("Inexistente")).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> {
            useCase.execute("Inexistente");
        });
    }
}