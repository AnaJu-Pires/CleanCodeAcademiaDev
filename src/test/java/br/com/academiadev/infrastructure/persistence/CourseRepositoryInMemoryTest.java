package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseRepositoryInMemoryTest {

    @Test
    void shouldSaveAndFindCourseByTitle() {
        CourseRepositoryInMemory repository = new CourseRepositoryInMemory();
        Course course = new Course("Java", "Desc", "Inst", 10, DifficultyLevel.BEGINNER);
        repository.save(course);
        Optional<Course> foundCourse = repository.findByTitle("Java");
        assertTrue(foundCourse.isPresent());
        assertEquals("Java", foundCourse.get().getTitle());
    }

    @Test
    void shouldReturnEmptyWhenCourseDoesNotExist() {
        CourseRepositoryInMemory repository = new CourseRepositoryInMemory();
        Optional<Course> foundCourse = repository.findByTitle("NaoExiste");
        assertTrue(foundCourse.isEmpty());
    }
}