package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.enums.SubscriptionPlan;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryInMemoryTest {

    @Test
    void shouldSaveAndFindStudentByEmail() {
        StudentRepositoryInMemory repository = new StudentRepositoryInMemory();
        Student student = new Student("Ana", "ana@email.com", SubscriptionPlan.BASIC);

        repository.save(student);
        Optional<Student> found = repository.findByEmail("ana@email.com");

        assertTrue(found.isPresent());
        assertEquals("Ana", found.get().getName());
    }

    @Test
    void shouldReturnAllStudents() {
        StudentRepositoryInMemory repository = new StudentRepositoryInMemory();
        repository.save(new Student("Ana", "ana@email.com", SubscriptionPlan.BASIC));
        repository.save(new Student("Joao", "joao@email.com", SubscriptionPlan.PREMIUM));

        Iterable<Student> all = repository.findAll();
        
        List<Student> list = new ArrayList<>();
        all.forEach(list::add);

        assertEquals(2, list.size());
    }
}