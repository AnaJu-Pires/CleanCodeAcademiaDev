package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.DomainException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends User {

    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments = new ArrayList<>(); 

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        if (subscriptionPlan == null) throw new DomainException("Plano é obrigatório");
        this.subscriptionPlan = subscriptionPlan;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public void enroll(Course course) {
        if (!course.isActive()) {
            throw new DomainException("Não é possível se matricular em curso inativo.");
        }
        boolean alreadyEnrolled = enrollments.stream()
                .anyMatch(e -> e.getCourse().getTitle().equals(course.getTitle())); // Compara por título ou objeto

        if (alreadyEnrolled) {
            throw new DomainException("Aluno já matriculado neste curso.");
        }
        if (enrollments.size() >= subscriptionPlan.getMaxActiveCourses()) {
            throw new DomainException("Limite de matrículas atingido para o plano " + subscriptionPlan);
        }
        Enrollment newEnrollment = new Enrollment(this, course);
        this.enrollments.add(newEnrollment);
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }
    
    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }
    
    public boolean isEnrolledIn(Course course) {
        return enrollments.stream().anyMatch(e -> e.getCourse().equals(course));
    }
}