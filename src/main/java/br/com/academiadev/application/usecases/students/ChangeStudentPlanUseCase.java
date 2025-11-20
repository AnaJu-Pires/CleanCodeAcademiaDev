package br.com.academiadev.application.usecases.students;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.BusinessException;

public class ChangeStudentPlanUseCase {

    private final StudentRepository repository;

    public ChangeStudentPlanUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(String email, SubscriptionPlan newPlan) {
        Student student = repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Student not found"));

        student.changeSubscriptionPlan(newPlan);

        repository.save(student);
    }
}
