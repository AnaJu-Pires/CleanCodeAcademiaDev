package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.DomainException;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    
    private SubscriptionPlan subscriptionPlan;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        validatePlan(subscriptionPlan);
        this.subscriptionPlan = subscriptionPlan;
    }

    public void enroll(Course course) {
        if (course == null) {
             throw new DomainException("Não é possível se matricular em um curso nulo.");
        }
        if (!course.isActive()) {
            throw new DomainException("Não é possível se matricular em um curso inativo.");
        }
        if (enrolledCourses.size() >= subscriptionPlan.getMaxActiveCourses()) {
            throw new DomainException("Plano não permite mais matrículas.");
        }
        if (enrolledCourses.contains(course)) {
             throw new DomainException("Aluno já está matriculado neste curso.");
        }
        this.enrolledCourses.add(course);
    }
    
    private void validatePlan(SubscriptionPlan plan) {
        if (plan == null) {
            throw new DomainException("O plano de assinatura é obrigatório.");
        }
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}