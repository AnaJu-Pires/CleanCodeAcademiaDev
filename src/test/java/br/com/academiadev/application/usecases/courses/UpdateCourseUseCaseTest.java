package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.DomainException;

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
public class UpdateCourseUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private UpdateCourseUseCase useCase;

    @Test
    void shouldUpdateAllFieldsSuccessfully() {
        Course existingCourse = new Course("Java", " Old Desc", "Prof Antigo", 10, DifficultyLevel.BEGINNER);

        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        Course updatedCourse = useCase.execute("Java", "Nova Desc", "Novo Prof", 20, DifficultyLevel.ADVANCED);

        assertEquals("Nova Desc", updatedCourse.getDescription());
        assertEquals("Novo Prof", updatedCourse.getInstructorName());
        assertEquals(20, updatedCourse.getDurationHours());
        assertEquals(DifficultyLevel.ADVANCED, updatedCourse.getDifficultyLevel());
        
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldUpdateOnlyProvidedFieldsAndKeepOthers() {
        Course existingCourse = new Course("Java", "Desc Original", "Prof Original", 10, DifficultyLevel.BEGINNER);

        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));


        Course updatedCourse = useCase.execute("Java", null, null, 30, null);

        assertEquals(30, updatedCourse.getDurationHours());
        
        assertEquals("Desc Original", updatedCourse.getDescription());
        assertEquals("Prof Original", updatedCourse.getInstructorName());
        assertEquals(DifficultyLevel.BEGINNER, updatedCourse.getDifficultyLevel());
        
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldIgnoreEmptyStringsAndInvalidNumbers() {
        Course existingCourse = new Course("Java", "Desc Original", "Prof Original", 10, DifficultyLevel.BEGINNER);

        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        Course updatedCourse = useCase.execute("Java", "", "   ", 0, null);

        assertEquals("Desc Original", updatedCourse.getDescription());
        assertEquals("Prof Original", updatedCourse.getInstructorName());
        assertEquals(10, updatedCourse.getDurationHours()); 
        
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findByTitle("Inexistente")).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> {
            useCase.execute("Inexistente", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        });

        verify(courseRepository, never()).save(any());
    }

    @Test
    void shouldKeepAllOriginalValuesWhenAllInputsAreNull() {
        Course existingCourse = new Course("Java", "Desc Original", "Prof Original", 10, DifficultyLevel.BEGINNER);

        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(existingCourse));

        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        Course updatedCourse = useCase.execute("Java", null, null, null, null);

        assertEquals("Desc Original", updatedCourse.getDescription());
        assertEquals("Prof Original", updatedCourse.getInstructorName());
        assertEquals(10, updatedCourse.getDurationHours());
        assertEquals(DifficultyLevel.BEGINNER, updatedCourse.getDifficultyLevel());
        
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}