package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.enums.CourseStatus;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.exceptions.BusinessException;

public class Course {
    
    private String title;
    private String description;
    private String instructorName;
    private int durationHours;
    private DifficultyLevel difficultyLevel;
    private CourseStatus status;

    public Course(String title, String description, String instructorName, int durationHours, DifficultyLevel difficultyLevel) {
        validateTitle(title);
        validateInstructorName(instructorName);
        validateDuration(durationHours);
        validateDifficulty(difficultyLevel);
        
        this.title = title;
        this.description = description; 
        this.instructorName = instructorName;
        this.durationHours = durationHours;
        this.difficultyLevel = difficultyLevel;
        this.status = CourseStatus.ACTIVE; 
    }
    
    
    public void inactivate() {
        this.status = CourseStatus.INACTIVE;
    }

    public void activate() {
        this.status = CourseStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.status == CourseStatus.ACTIVE;
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new BusinessException("O título do curso não pode ser vazio.");
        }
    }

    private void validateInstructorName(String instructorName) {
        if (instructorName == null || instructorName.trim().isEmpty()) {
            throw new BusinessException("O nome do instrutor não pode ser vazio.");
        }
    }

    private void validateDuration(int duration) {
        if (duration <= 0) {
            throw new BusinessException("A carga horária deve ser maior que zero.");
        }
    }

    private void validateDifficulty(DifficultyLevel level) {
        if (level == null) {
            throw new BusinessException("O nível de dificuldade é obrigatório.");
        }
    }

    public String getTitle() { 
        return title; 
    }
    public String getDescription() { 
        return description; 
    }
    public String getInstructorName() { 
        return instructorName; 
    }
    public int getDurationHours() { 
        return durationHours; 
    }
    public DifficultyLevel getDifficultyLevel() { 
        return difficultyLevel; 
    }
    public CourseStatus getStatus() { 
        return status; 
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }
    public void updateInstructorName(String newInstructorName) {
        this.instructorName = newInstructorName;
    }
    public void updateDurationHours(Integer newDurationHours) {
        this.durationHours = newDurationHours;
    }
    public void updateDifficultyLevel(DifficultyLevel newDifficultyLevel) {
        this.difficultyLevel = newDifficultyLevel;
    }
}