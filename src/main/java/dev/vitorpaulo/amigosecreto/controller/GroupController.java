package dev.vitorpaulo.amigosecreto.controller;

import dev.vitorpaulo.amigosecreto.domain.Group;
import dev.vitorpaulo.amigosecreto.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@RequestMapping("/group/v1")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/{id}/user")
    public Group addUser(@PathVariable Long id, @RequestParam Long userId) {
        return groupService.addUser(id, userId);
    }

    @PostMapping("/{id}/sort")
    public Group sortUsers(@PathVariable Long id) {
        return groupService.sortUsers(id);
    }

    @PostMapping("/{id}/verify")
    public String verifySort(@PathVariable Long id) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return groupService.verifySort(id);
    }
}
