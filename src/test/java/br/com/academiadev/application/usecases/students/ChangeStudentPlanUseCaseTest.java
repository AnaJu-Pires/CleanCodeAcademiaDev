package br.com.academiadev.application.usecases.students;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
public class ChangeStudentPlanUseCaseTest {
    
    @Mock
    private StudentRepository repository;

    @InjectMocks
    private ChangeStudentPlanUseCase useCase;

    @Test
    void shouldChangeStudentPlanSuccessfully() {
        Student s1 = new Student("Ana", "ana@test.com", SubscriptionPlan.BASIC);
        when(repository.findByEmail("ana@test.com")).thenReturn(Optional.of(s1));
        when(repository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        useCase.execute("ana@test.com", SubscriptionPlan.PREMIUM);
        
        assertEquals(SubscriptionPlan.PREMIUM, s1.getSubscriptionPlan());
        verify(repository, times(1)).save(s1);
    }

    @Test
    void shouldThrowExceptionWhenDowngradingWithTooManyCourses() {
        Student student = new Student("Ana", "ana@test.com", SubscriptionPlan.PREMIUM);
        student.enroll(new Course("C1", "D", "P", 10, DifficultyLevel.BEGINNER));
        student.enroll(new Course("C2", "D", "P", 10, DifficultyLevel.BEGINNER));
        student.enroll(new Course("C3", "D", "P", 10, DifficultyLevel.BEGINNER));
        student.enroll(new Course("C4", "D", "P", 10, DifficultyLevel.BEGINNER));
        
        when(repository.findByEmail("ana@test.com")).thenReturn(Optional.of(student));

        assertThrows(BusinessException.class, () -> {
            useCase.execute("ana@test.com", SubscriptionPlan.BASIC);
        });
        
        assertEquals(SubscriptionPlan.PREMIUM, student.getSubscriptionPlan());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionIfStudentNotFound() {
        when(repository.findByEmail("ana@test.com")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            useCase.execute("ana@test.com", SubscriptionPlan.PREMIUM);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionIfPlanIsSame() {
        Student s1 = new Student("Ana", "ana@test.com", SubscriptionPlan.BASIC);
        when(repository.findByEmail("ana@test.com")).thenReturn(Optional.of(s1));

        assertThrows(BusinessException.class, () -> {
            useCase.execute("ana@test.com", SubscriptionPlan.BASIC);
        });

        verify(repository, never()).save(any());
    }
}