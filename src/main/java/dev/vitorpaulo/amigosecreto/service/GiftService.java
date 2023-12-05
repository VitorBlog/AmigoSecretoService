package dev.vitorpaulo.amigosecreto.service;

import dev.vitorpaulo.amigosecreto.domain.Gift;
import dev.vitorpaulo.amigosecreto.domain.User;
import dev.vitorpaulo.amigosecreto.repository.GiftRepository;
import dev.vitorpaulo.amigosecreto.repository.UserRepository;
import dev.vitorpaulo.amigosecreto.request.CreateGiftRequest;
import dev.vitorpaulo.amigosecreto.response.GetGiftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;
    private final UserRepository userRepository;

    public GetGiftResponse createGift(CreateGiftRequest request, User user) {
        final var gift = giftRepository.save(
                Gift.builder()
                        .name(request.getName())
                        .link(request.getLink())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        user.getGiftList().add(gift);
        userRepository.save(user);

        return mapGift(gift);
    }

    public GetGiftResponse getGift(Long id, User user) {
        return user.getGiftList()
                .stream()
                .filter(value -> value.getId().equals(id))
                .map(GiftService::mapGift)
                .findFirst()
                .orElseThrow();
    }

    public GetGiftResponse updateGift(CreateGiftRequest request, User user) {
        final var gift = giftRepository.save(
                Gift.builder()
                        .id(request.getId())
                        .name(request.getName())
                        .link(request.getLink())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        return mapGift(gift);
    }

    public void deleteGift(Long id, User user) {
        user.setGiftList(user.getGiftList().stream().filter(value -> !value.getId().equals(id)).toList());
        userRepository.save(user);

        if (user.getGiftList().stream().anyMatch(gift -> gift.getId().equals(id))) {
            giftRepository.deleteById(id);
        }
    }

    public static GetGiftResponse mapGift(Gift gift) {
        return GetGiftResponse.builder()
                .id(gift.getId())
                .name(gift.getName())
                .link(gift.getLink())
                .build();
    }
}
