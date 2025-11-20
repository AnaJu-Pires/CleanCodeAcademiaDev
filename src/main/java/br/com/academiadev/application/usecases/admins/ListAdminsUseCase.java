package br.com.academiadev.application.usecases.admins;

import br.com.academiadev.application.repositories.AdminRepository;
import br.com.academiadev.domain.entities.Admin;

public class ListAdminsUseCase {
    
    private final AdminRepository repository;

    public ListAdminsUseCase(AdminRepository repository) {
        this.repository = repository;
    }

    public Iterable<Admin> execute() {
        return repository.findAll();
    }
}
