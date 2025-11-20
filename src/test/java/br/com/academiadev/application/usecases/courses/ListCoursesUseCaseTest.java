package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCoursesUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ListCoursesUseCase useCase;

    @Test
    void shouldListAllCoursesSuccessfully() {
        Course course1 = new Course("Java", "Desc1", "Prof1", 10, DifficultyLevel.BEGINNER);
        Course course2 = new Course("Python", "Desc2", "Prof2", 15, DifficultyLevel.INTERMEDIATE);

        when(courseRepository.findAll()).thenReturn(List.of(course1, course2));

        Iterable<Course> courses = useCase.execute();

        assertNotNull(courses);
        
        List<Course> courseList = new ArrayList<>();
        courses.forEach(courseList::add);

        assertEquals(2, courseList.size());

        assertEquals("Java", courseList.get(0).getTitle());
        assertEquals("Desc1", courseList.get(0).getDescription());
        assertEquals("Prof1", courseList.get(0).getInstructorName());
        assertEquals(10, courseList.get(0).getDurationHours());
        assertEquals(DifficultyLevel.BEGINNER, courseList.get(0).getDifficultyLevel());
        assertTrue(courseList.get(0).isActive());

        assertEquals("Python", courseList.get(1).getTitle());
        assertEquals("Desc2", courseList.get(1).getDescription());
        assertEquals("Prof2", courseList.get(1).getInstructorName());
        assertEquals(15, courseList.get(1).getDurationHours());
        assertEquals(DifficultyLevel.INTERMEDIATE, courseList.get(1).getDifficultyLevel());
        assertTrue(courseList.get(1).isActive());
        
        verify(courseRepository, times(1)).findAll();
    }
}