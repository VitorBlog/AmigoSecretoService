package dev.vitorpaulo.amigosecreto.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private Boolean seenFriend;

    @Lob
    private String password;

    @ManyToOne
    private User secretFriend;

    @ManyToOne
    private Group familyGroup;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Gift> giftList;
}
