package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.exceptions.DomainException;


public class ActivateCourseUseCase {
    
    private final CourseRepository repository;

    public ActivateCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Course execute(String title) {
        Course course = repository.findByTitle(title)
                .orElseThrow(() -> new DomainException("No course found with the title: " + title));

        if (course.isActive()) {
            System.out.println("Course is already active.");
            return course;
        }

        course.activate();
        return repository.save(course);
    }
}
