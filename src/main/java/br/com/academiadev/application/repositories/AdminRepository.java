package br.com.academiadev.application.repositories;
import br.com.academiadev.domain.entities.Admin;
import java.util.Optional;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findByEmail(String email);
    Iterable<Admin> findAll();
}
