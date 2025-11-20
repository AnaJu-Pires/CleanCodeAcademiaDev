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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchCourseUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SearchCourseUseCase useCase;

    @Test
    void shouldSearchCourseSuccessfully() {
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);

        when(courseRepository.findByTitle("Java")).thenReturn(java.util.Optional.of(course));

        Optional<Course> foundCourse = useCase.execute("Java");

        assertNotNull(foundCourse);
        assertEquals("Java", foundCourse.get().getTitle());
        assertEquals("Desc", foundCourse.get().getDescription());
        assertEquals("Prof", foundCourse.get().getInstructorName());
        assertEquals(10, foundCourse.get().getDurationHours());
        assertEquals(DifficultyLevel.BEGINNER, foundCourse.get().getDifficultyLevel());
        assertTrue(foundCourse.get().isActive());

        verify(courseRepository, times(1)).findByTitle("Java");
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findByTitle("Inexistente")).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> {
            useCase.execute("Inexistente");
        });

        verify(courseRepository, times(1)).findByTitle("Inexistente");
    }
}
