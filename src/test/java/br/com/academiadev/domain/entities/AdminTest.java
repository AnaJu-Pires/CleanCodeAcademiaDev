package br.com.academiadev.domain.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void shouldCreateAdminSuccessfully() {
        Admin admin = new Admin("Admin User", "admin@academiadev.com");

        assertNotNull(admin);
        assertEquals("Admin User", admin.getName());
        assertEquals("admin@academiadev.com", admin.getEmail());
        assertEquals("Admin", admin.getRole());
    }
}