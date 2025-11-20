package br.com.academiadev.application.usecases.courses;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.BusinessException;

public class UpdateCourseUseCase {

    private final CourseRepository repository;

    public UpdateCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Course execute(String title, String newDescription, String newInstructor, Integer newHours, DifficultyLevel newDifficultyLevel) {

        Course course = repository.findByTitle(title)
                .orElseThrow(() -> new BusinessException("No course found with the title: " + title));

        if (newDescription != null && !newDescription.isBlank()) {
            course.updateDescription(newDescription);
        }
        if (newInstructor != null && !newInstructor.isBlank()) {
            course.updateInstructorName(newInstructor);
        }
        if (newHours != null && newHours > 0) {
            course.updateDurationHours(newHours);
        }
        if ( newDifficultyLevel != null) {
            course.updateDifficultyLevel(newDifficultyLevel);
        }

        return repository.save(course);
    }
    
}