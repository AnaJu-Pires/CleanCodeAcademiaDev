package br.com.academiadev.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.academiadev.domain.entities.Admin;

public class AdminRepositoryInMemoryTest {

    @Test
    void shouldSaveAndFindAdminByEmail() {
        AdminRepositoryInMemory repository = new AdminRepositoryInMemory();
        Admin admin = new Admin("Carlos", "admin@example.com");
        repository.save(admin);
        var found = repository.findByEmail("admin@example.com");

        assertTrue(found.isPresent());
        assertEquals("Carlos", found.get().getName());
    }

    @Test
    void shouldReturnAllAdmins() {
        AdminRepositoryInMemory repository = new AdminRepositoryInMemory();
        repository.save(new Admin("Carlos", "admin@example.com"));
        repository.save(new Admin("Ana", "admin2@example.com"));
        Iterable<Admin> admins = repository.findAll();
        int count = 0;
        for (Admin admin : admins) {
            count++;
            assertNotNull(admin.getName());
        }
        assertEquals(2, count);
    }
    
}
