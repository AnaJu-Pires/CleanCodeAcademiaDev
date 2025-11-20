package br.com.academiadev.application.usecases.admins;

import br.com.academiadev.application.repositories.AdminRepository;
import br.com.academiadev.domain.entities.Admin;
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
public class RegisterAdminUseCaseTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private RegisterAdminUseCase useCase;

    @Test
    void shouldRegisterAdminSuccessfully() {
        when(repository.findByEmail("admin@example.com")).thenReturn(Optional.empty());

        when(repository.save(any(Admin.class))).thenAnswer(i -> i.getArgument(0));

        Admin createdAdmin = useCase.execute("Carlos", "admin@example.com");

        assertNotNull(createdAdmin);
        assertEquals("Carlos", createdAdmin.getName());
        assertEquals("admin@example.com", createdAdmin.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        Admin existingAdmin = new Admin("Outra Pessoa", "admin@example.com");
        when(repository.findByEmail("admin@example.com")).thenReturn(Optional.of(existingAdmin));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            useCase.execute("Carlos", "admin@example.com");
        });
        assertEquals("There is already an admin registered with the email admin@example.com", exception.getMessage());
        verify(repository, never()).save(any(Admin.class));
    }
    
}
