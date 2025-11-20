package br.com.academiadev.infrastructure.persistence;
import java.util.ArrayList;
import java.util.Optional;

import br.com.academiadev.application.repositories.AdminRepository;
import br.com.academiadev.domain.entities.Admin;

public class AdminRepositoryInMemory implements AdminRepository {

    private final java.util.Map<String, Admin> database = new java.util.HashMap<>();

    @Override
    public Admin save(Admin admin) {
        database.put(admin.getEmail(), admin);
        return admin;
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return Optional.ofNullable(database.get(email));
    }

    @Override
    public Iterable<Admin> findAll() {
        return new ArrayList<>(database.values());
    }
    
}
