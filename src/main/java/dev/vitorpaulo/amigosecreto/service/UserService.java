package dev.vitorpaulo.amigosecreto.service;

import dev.vitorpaulo.amigosecreto.domain.Gift;
import dev.vitorpaulo.amigosecreto.domain.User;
import dev.vitorpaulo.amigosecreto.exception.UserNotFoundException;
import dev.vitorpaulo.amigosecreto.repository.UserRepository;
import dev.vitorpaulo.amigosecreto.response.GetGiftResponse;
import dev.vitorpaulo.amigosecreto.response.UserHomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserHomeResponse viewHome(User user) {
        final var isSorted = user.getGroup() != null && user.getGroup().getSorted();

        return UserHomeResponse.builder()
                .sorted(isSorted)
                .name(user.getName())
                .seenFriend(user.getSeenFriend())
                .secretFriend(isSorted? user.getSecretFriend().getName() : null)
                .gifts(user.getGiftList().stream().map(GiftService::mapGift).toList())
                .friendGifts(isSorted? user.getSecretFriend().getGiftList().stream().map(GiftService::mapGift).toList() : Collections.emptyList())
                .build();
    }

    public UserHomeResponse updateSeen(User user) {
        user.setSeenFriend(true);
        userRepository.save(user);

        return viewHome(user);
    }

    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
