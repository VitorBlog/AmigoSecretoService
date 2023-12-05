package dev.vitorpaulo.amigosecreto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class UserHomeResponse implements Serializable {

    private final String name;
    private final Boolean sorted;
    private final Boolean seenFriend;
    private final String secretFriend;
    private final List<GetGiftResponse> gifts;
    private final List<GetGiftResponse> friendGifts;
}
