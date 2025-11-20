package br.com.academiadev.application.usecases.admins;

import br.com.academiadev.application.repositories.AdminRepository;
import br.com.academiadev.domain.entities.Admin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListAdminsUseCaseTest {
    
    @Mock
    private AdminRepository repository;

    @InjectMocks
    private ListAdminsUseCase useCase;

    @Test
    void shouldListAllAdminsSuccessfully() {
        Admin admin1 = new Admin("Carlos", "admin@example.com");
        Admin admin2 = new Admin("Ana", "admin2@example.com");
        when(repository.findAll()).thenReturn(List.of(admin1, admin2));
        Iterable<Admin> admins = useCase.execute();
        assertNotNull(admins);
        assertEquals(2, admins.spliterator().getExactSizeIfKnown());
    }
}
