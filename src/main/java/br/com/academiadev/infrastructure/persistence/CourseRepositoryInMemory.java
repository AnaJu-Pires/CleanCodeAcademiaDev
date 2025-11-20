package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.domain.entities.Course;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CourseRepositoryInMemory implements CourseRepository {

    private final Map<String, Course> database = new HashMap<>();

    @Override
    public Course save(Course course) {
        database.put(course.getTitle(), course);
        return course;
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(database.get(title));
    }

    @Override
    public Iterable<Course> findAll() {
        return database.values();
    }

}