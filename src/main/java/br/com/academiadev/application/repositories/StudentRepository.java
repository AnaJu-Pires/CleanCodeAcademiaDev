package br.com.academiadev.application.repositories;
import br.com.academiadev.domain.entities.Student;

import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findByEmail(String email);
    Iterable<Student> findAll();
}
