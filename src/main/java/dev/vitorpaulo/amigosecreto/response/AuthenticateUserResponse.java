package dev.vitorpaulo.amigosecreto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class AuthenticateUserResponse implements Serializable {

    private final String accessToken;
    private final String refreshToken;
}
