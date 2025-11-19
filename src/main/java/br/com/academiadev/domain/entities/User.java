package br.com.academiadev.domain.entities;

import br.com.academiadev.domain.exceptions.DomainException;

public abstract class User {
    protected String name;
    protected String email;

    public User(String name, String email) {
        validateName(name);
        validateEmail(email);
        this.name = name;
        this.email = email;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException("O nome do usuário é obrigatório.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new DomainException("O email do usuário é obrigatório.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new DomainException("O email do usuário é inválido.");
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}