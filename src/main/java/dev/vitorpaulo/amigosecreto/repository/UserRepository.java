package dev.vitorpaulo.amigosecreto.repository;

import dev.vitorpaulo.amigosecreto.domain.Group;
import dev.vitorpaulo.amigosecreto.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByGroup(Group group);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}