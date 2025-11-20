package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.Course;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findByTitle(String title);
    Iterable<Course> findAll();
}