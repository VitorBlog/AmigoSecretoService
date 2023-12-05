package dev.vitorpaulo.amigosecreto.response;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class GetGiftResponse implements Serializable {

    private final Long id;
    private final String name;
    private final String link;
    private final LocalDateTime updatedAt;
}
