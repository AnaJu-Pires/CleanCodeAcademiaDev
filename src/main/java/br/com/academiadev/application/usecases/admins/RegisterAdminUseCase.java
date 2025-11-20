package br.com.academiadev.application.usecases.admins;

import br.com.academiadev.application.repositories.AdminRepository;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.exceptions.BusinessException;

public class RegisterAdminUseCase {

    private final AdminRepository repository;

    public RegisterAdminUseCase(AdminRepository repository) {
        this.repository = repository;
    }

    public Admin execute(String name, String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new BusinessException("There is already an admin registered with the email " + email);
        }

        Admin newAdmin = new Admin(name, email);

        return repository.save(newAdmin);
    }
    
}
