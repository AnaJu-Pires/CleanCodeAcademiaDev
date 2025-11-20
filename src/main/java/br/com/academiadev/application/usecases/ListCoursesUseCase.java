package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

public class ListCoursesUseCase {

    private final CourseRepository repository;

    public ListCoursesUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Iterable<Course> execute() {
        return repository.findAll();
    }

}
