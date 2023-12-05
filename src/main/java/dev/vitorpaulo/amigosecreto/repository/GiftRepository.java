package dev.vitorpaulo.amigosecreto.repository;

import dev.vitorpaulo.amigosecreto.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}