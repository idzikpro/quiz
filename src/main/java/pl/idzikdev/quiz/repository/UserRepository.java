package pl.idzikdev.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.idzikdev.quiz.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>  findByusername(String username);
}
