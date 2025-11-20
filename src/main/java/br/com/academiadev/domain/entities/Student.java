package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends User {

    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments = new ArrayList<>(); 

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        if (subscriptionPlan == null) throw new BusinessException("The subscription plan cannot be null.");
        this.subscriptionPlan = subscriptionPlan;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public void enroll(Course course) {
        if (!course.isActive()) {
            throw new EnrollmentException("It is not possible to enroll in an inactive course.");
        }
        boolean alreadyEnrolled = enrollments.stream()
                .anyMatch(e -> e.getCourse().getTitle().equals(course.getTitle()));

        if (alreadyEnrolled) {
            throw new EnrollmentException("Student is already enrolled in this course.");
        }
        if (enrollments.size() >= subscriptionPlan.getMaxActiveCourses()) {
            throw new EnrollmentException("Enrollment limit reached for plan " + subscriptionPlan);
        }
        Enrollment newEnrollment = new Enrollment(this, course);
        this.enrollments.add(newEnrollment);
    }

    public void changeSubscriptionPlan(SubscriptionPlan newPlan) {
        if (newPlan == null) {
            throw new BusinessException("The new plan cannot be null.");
        }
        
        if (this.subscriptionPlan == newPlan) {
            throw new BusinessException("The student already has the plan " + newPlan);
        }

        int newLimit = newPlan.getMaxActiveCourses();
        int currentEnrollments = this.enrollments.size();

        if (currentEnrollments > newLimit) {
            throw new BusinessException(
                String.format("It is not possible to change to the plan %s. The student has %d enrollments, but the plan allows only %d.",
                newPlan, currentEnrollments, newLimit)
            );
        }

        this.subscriptionPlan = newPlan;
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