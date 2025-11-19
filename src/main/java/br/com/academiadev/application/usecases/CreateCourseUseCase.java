package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.DomainException;

public class CreateCourseUseCase {

    private final CourseRepository repository;

    public CreateCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Course execute(String title, String description, String instructor, int hours, DifficultyLevel level) {
        
        if (repository.findByTitle(title).isPresent()) {
            throw new DomainException("Já existe um curso com o título '" + title + "'.");
        }

        Course newCourse = new Course(title, description, instructor, hours, level);
        return repository.save(newCourse);
    }
}