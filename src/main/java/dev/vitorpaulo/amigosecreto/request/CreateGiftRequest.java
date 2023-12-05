package dev.vitorpaulo.amigosecreto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateGiftRequest implements Serializable {

    private Long id;
    private String name;
    private String link;
}
