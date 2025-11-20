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
class CreateCourseUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CreateCourseUseCase useCase;

    @Test
    void shouldCreateCourseSuccessfully() {
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.empty());

        when(courseRepository.save(any(Course.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Course createdCourse = useCase.execute("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);

        assertNotNull(createdCourse);
        assertEquals("Java", createdCourse.getTitle());
        
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldThrowExceptionWhenTitleAlreadyExists() {

        Course existingCourse = new Course("Java", "D", "P", 10, DifficultyLevel.BEGINNER);
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(existingCourse));

        assertThrows(DomainException.class, () -> {
            useCase.execute("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        });

        verify(courseRepository, never()).save(any(Course.class));
    }
}