package br.com.academiadev.application.usecases.students;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import br.com.academiadev.domain.exceptions.BusinessException;
import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;

@ExtendWith(MockitoExtension.class)
public class SearchStudentUseCaseTest {
    
    @Mock
    private StudentRepository repository;

    @InjectMocks
    private SearchStudentUseCase useCase;

    @Test
    void shouldSearchStudent() {
        Student student = new Student("Ana", "ana@teste.com", SubscriptionPlan.BASIC);
        when(repository.findByEmail("ana@teste.com")).thenReturn(Optional.of(student));

        Student result = useCase.execute("ana@teste.com");
        assertNotNull(result);
        assertEquals("Ana", result.getName());
        assertEquals("ana@teste.com", result.getEmail());
        assertEquals(SubscriptionPlan.BASIC, result.getSubscriptionPlan());

        verify(repository, times(1)).findByEmail("ana@teste.com");

    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(repository.findByEmail("ana@teste.com")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            useCase.execute("ana@teste.com");
        });
    }

}
