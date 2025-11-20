package br.com.academiadev.application.usecases.courses;

import java.util.Optional;
import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.exceptions.DomainException;

public class SearchCourseUseCase {
      private final CourseRepository repository;

    public SearchCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Optional<Course> execute(String title) {
        Course course = repository.findByTitle(title)
            .orElseThrow(() -> new DomainException("No course found with the title: " + title));

        return Optional.of(course);
    }

}
