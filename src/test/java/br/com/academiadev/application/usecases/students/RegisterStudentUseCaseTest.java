package br.com.academiadev.application.usecases.students;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterStudentUseCaseTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private RegisterStudentUseCase useCase;

    @Test
    void shouldRegisterStudentSuccessfully() {
        when(repository.findByEmail("ana@email.com")).thenReturn(Optional.empty());

        when(repository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student createdStudent = useCase.execute("Ana", "ana@email.com", SubscriptionPlan.PREMIUM);

        assertNotNull(createdStudent);
        assertEquals("Ana", createdStudent.getName());
        assertEquals("ana@email.com", createdStudent.getEmail());
        assertEquals(SubscriptionPlan.PREMIUM, createdStudent.getSubscriptionPlan());

        verify(repository, times(1)).save(any(Student.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        Student existingStudent = new Student("Outra Pessoa", "ana@email.com", SubscriptionPlan.BASIC);
        when(repository.findByEmail("ana@email.com")).thenReturn(Optional.of(existingStudent));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            useCase.execute("Ana", "ana@email.com", SubscriptionPlan.PREMIUM);
        });
        assertEquals("There is already a student registered with the email ana@email.com", exception.getMessage());
        verify(repository, never()).save(any(Student.class));
    }
}