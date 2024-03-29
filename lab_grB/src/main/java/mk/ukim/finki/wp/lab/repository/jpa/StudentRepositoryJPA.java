package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepositoryJPA extends JpaRepository<Student, String> {
    List<Student> findByNameContainsOrSurnameContains(String name, String surname);
    Optional<Student> findFirstByUsernameContaining(String username);
    Optional<Student> findByUsername(String username);
}
