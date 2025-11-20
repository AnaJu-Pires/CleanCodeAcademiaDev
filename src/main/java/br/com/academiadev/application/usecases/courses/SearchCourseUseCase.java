package br.com.academiadev.application.usecases.courses;

import java.util.Optional;
import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

public class SearchCourseUseCase {
      private final CourseRepository repository;

    public SearchCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Optional<Course> execute(String title) {
        return repository.findByTitle(title);
    }

}
