package dev.vitorpaulo.amigosecreto.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity(name = "family_group")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Boolean sorted;
}
