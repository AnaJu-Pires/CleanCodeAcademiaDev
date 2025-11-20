package br.com.academiadev.application.usecases.students;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.BusinessException;

public class RegisterStudentUseCase {

    private final StudentRepository repository;

    public RegisterStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public Student execute(String name, String email, SubscriptionPlan plan) {
        if (repository.findByEmail(email).isPresent()) {
            throw new BusinessException("There is already a student registered with the email " + email);
        }

        Student newStudent = new Student(name, email, plan);

        return repository.save(newStudent);
    }
}