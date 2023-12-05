package dev.vitorpaulo.amigosecreto.service;

import dev.vitorpaulo.amigosecreto.domain.User;
import dev.vitorpaulo.amigosecreto.exception.InvalidPasswordException;
import dev.vitorpaulo.amigosecreto.exception.UserAlreadyExistsException;
import dev.vitorpaulo.amigosecreto.exception.UserNotFoundException;
import dev.vitorpaulo.amigosecreto.repository.UserRepository;
import dev.vitorpaulo.amigosecreto.request.AuthenticateUserRequest;
import dev.vitorpaulo.amigosecreto.response.AuthenticateUserResponse;
import io.jsonwebtoken.JwtBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.expiration:2592000000}")
    private Long jwtExpiration;

    private final JwtBuilder jwtBuilder;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticateUserResponse register(AuthenticateUserRequest request) throws UserAlreadyExistsException {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        final var user = User.builder()
                .seenFriend(false)
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return createToken(userRepository.save(user));
    }

    public AuthenticateUserResponse login(AuthenticateUserRequest request) throws UserNotFoundException, InvalidPasswordException {
        final var user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return createToken(user);
    }

    protected AuthenticateUserResponse createToken(User user) {
        return AuthenticateUserResponse.builder()
                .accessToken(
                        jwtBuilder
                                .setSubject(user.getId().toString())
                                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                                .compact()
                )
                .build();
    }
}
