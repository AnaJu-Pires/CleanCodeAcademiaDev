package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

public class StudentRepositoryInMemory implements StudentRepository {

    private final Map<String, Student> database = new HashMap<>();

    @Override
    public Student save(Student student) {
        database.put(student.getEmail(), student);
        return student;
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return Optional.ofNullable(database.get(email));
    }

    @Override
    public Iterable<Student> findAll() {
        return new ArrayList<>(database.values());
    }
}