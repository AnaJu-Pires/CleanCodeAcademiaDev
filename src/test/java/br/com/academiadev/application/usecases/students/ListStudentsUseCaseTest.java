package br.com.academiadev.application.usecases.students;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListStudentsUseCaseTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private ListStudentsUseCase useCase;

    @Test
    void shouldListAllStudents() {
        Student s1 = new Student("Ana", "ana@test.com", SubscriptionPlan.BASIC);
        Student s2 = new Student("Bob", "bob@test.com", SubscriptionPlan.PREMIUM);

        when(repository.findAll()).thenReturn(List.of(s1, s2));

        List<Student> result = useCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ana", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        
        verify(repository, times(1)).findAll();
    }
}