package dev.vitorpaulo.amigosecreto.controller;

import dev.vitorpaulo.amigosecreto.domain.User;
import dev.vitorpaulo.amigosecreto.model.DetailedUser;
import dev.vitorpaulo.amigosecreto.request.CreateGiftRequest;
import dev.vitorpaulo.amigosecreto.response.GetGiftResponse;
import dev.vitorpaulo.amigosecreto.response.UserHomeResponse;
import dev.vitorpaulo.amigosecreto.service.GiftService;
import dev.vitorpaulo.amigosecreto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/gift/v1")
public class GiftController {

    private final GiftService giftService;

    @PostMapping
    public GetGiftResponse createGift(@RequestBody CreateGiftRequest request, @AuthenticationPrincipal User user) {
        return giftService.createGift(request, user);
    }

    @GetMapping("/{id}/view")
    public GetGiftResponse getGift(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return giftService.getGift(id, user);
    }

    @PutMapping
    public GetGiftResponse updateGift(@RequestBody CreateGiftRequest request, @AuthenticationPrincipal User user) {
        return giftService.updateGift(request, user);
    }

    @DeleteMapping("/{id}")
    public void deleteGift(@PathVariable Long id, @AuthenticationPrincipal User user) {
        giftService.deleteGift(id, user);
    }
}
