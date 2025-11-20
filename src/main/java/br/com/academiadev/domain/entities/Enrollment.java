package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.exceptions.*;
import java.time.LocalDateTime;

public class Enrollment {

    private Student student;
    private Course course;
    private int watchedHours;
    private LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        if (student == null || course == null) {
            throw new BusinessException("Aluno e Curso são obrigatórios para a matrícula.");
        }
        
        this.student = student;
        this.course = course;
        this.watchedHours = 0;
        this.enrollmentDate = LocalDateTime.now();
    }

    public void addWatchedHours(int hoursToAdd) {
        if (hoursToAdd < 0) {
            throw new EnrollmentException("As horas assistidas não podem ser negativas.");
        }

        int courseDuration = course.getDurationHours();
        int newTotal = this.watchedHours + hoursToAdd;

        if (newTotal > courseDuration) {
            throw new EnrollmentException(
                "A carga horária total (" + newTotal + "h) excede a duração do curso (" + courseDuration + "h)."
            );
        }
        
        this.watchedHours = newTotal;
    }

    public int getProgressPercentage() {
        int totalHours = course.getDurationHours();
        if (totalHours == 0) return 0;
        
        return (watchedHours * 100) / totalHours;
    }

    public boolean isCompleted() {
        return getProgressPercentage() == 100;
    }

    public int getWatchedHours() { 
        return watchedHours; 
    }
    public Course getCourse() {
        return course;
    }
    public Student getStudent() {
        return student;
    }
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
}