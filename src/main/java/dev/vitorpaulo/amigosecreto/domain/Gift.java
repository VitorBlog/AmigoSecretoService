package dev.vitorpaulo.amigosecreto.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gift {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String name;

    @Lob
    private String link;

    private LocalDateTime updatedAt;
}
