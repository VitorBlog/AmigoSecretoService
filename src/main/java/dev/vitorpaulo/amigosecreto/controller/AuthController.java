package dev.vitorpaulo.amigosecreto.controller;

import dev.vitorpaulo.amigosecreto.exception.InvalidPasswordException;
import dev.vitorpaulo.amigosecreto.exception.UserAlreadyExistsException;
import dev.vitorpaulo.amigosecreto.exception.UserNotFoundException;
import dev.vitorpaulo.amigosecreto.request.AuthenticateUserRequest;
import dev.vitorpaulo.amigosecreto.response.AuthenticateUserResponse;
import dev.vitorpaulo.amigosecreto.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthenticateUserResponse register(@RequestBody AuthenticateUserRequest request) throws UserAlreadyExistsException {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthenticateUserResponse login(@RequestBody AuthenticateUserRequest request) throws UserNotFoundException, InvalidPasswordException {
        return authService.login(request);
    }
}
