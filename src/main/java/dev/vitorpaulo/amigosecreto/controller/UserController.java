package dev.vitorpaulo.amigosecreto.controller;

import dev.vitorpaulo.amigosecreto.domain.User;
import dev.vitorpaulo.amigosecreto.model.DetailedUser;
import dev.vitorpaulo.amigosecreto.response.UserHomeResponse;
import dev.vitorpaulo.amigosecreto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public UserHomeResponse getHome(@AuthenticationPrincipal User user) {
        return userService.viewHome(user);
    }

    @PutMapping("/seen")
    public UserHomeResponse updateSeen(@AuthenticationPrincipal User user) {
        return userService.updateSeen(user);
    }
}
