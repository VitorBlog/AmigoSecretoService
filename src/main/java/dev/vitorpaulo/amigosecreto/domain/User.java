package dev.vitorpaulo.amigosecreto.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
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
