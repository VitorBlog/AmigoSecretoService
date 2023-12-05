package dev.vitorpaulo.amigosecreto.repository;

import dev.vitorpaulo.amigosecreto.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}