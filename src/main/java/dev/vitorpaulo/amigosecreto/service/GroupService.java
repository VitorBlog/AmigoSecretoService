package dev.vitorpaulo.amigosecreto.service;

import dev.vitorpaulo.amigosecreto.domain.Gift;
import dev.vitorpaulo.amigosecreto.domain.Group;
import dev.vitorpaulo.amigosecreto.domain.User;
import dev.vitorpaulo.amigosecreto.repository.GiftRepository;
import dev.vitorpaulo.amigosecreto.repository.GroupRepository;
import dev.vitorpaulo.amigosecreto.repository.UserRepository;
import dev.vitorpaulo.amigosecreto.request.CreateGiftRequest;
import dev.vitorpaulo.amigosecreto.response.GetGiftResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public Group addUser(Long id, Long userId) {
        final var group = findGroup(id);
        final var user = userRepository.findById(userId).orElseThrow();

        user.setGroup(group);
        userRepository.save(user);

        return group;
    }

    public Group sortUsers(Long id) {
        final var group = findGroup(id);
        final var users = userRepository.findByGroup(group);
        final var list = new ArrayList<>(users);

        for (User user : users) {
            final var result = list.stream()
                    .filter(value -> !value.getId().equals(user.getId()))
                    .toList()
                    .get(new Random().nextInt(0, list.size() > 1? list.size() - 1 : 1));
            list.remove(result);

            user.setSecretFriend(result);
            userRepository.save(user);
        }

        group.setSorted(true);
        groupRepository.save(group);

        return group;
    }

    public String verifySort(Long id) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        final var group = findGroup(id);
        final var users = userRepository.findByGroup(group);
        StringBuilder label = new StringBuilder(group.getName() + "\n\n");

        for (User user : users) {
            final var bestFriends = Objects.equals(user.getId(), user.getSecretFriend().getSecretFriend().getId());

            label.append(md5hex(user.getName()))
                    .append(" (").append(user.getGiftList().size()).append(")")
                    .append(bestFriends? " <-> " : " -> ")
                    .append(md5hex(user.getSecretFriend().getName()))
                    .append(" (").append(user.getSecretFriend().getGiftList().size()).append(") ")
                    .append("\n\n");
        }

        return label.toString();
    }

    private String md5hex(String name) throws NoSuchAlgorithmException {
        final var messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(name.getBytes());

        final var stringBuilder = new StringBuilder();
        for (var value : messageDigest.digest()) {
            stringBuilder.append(Integer.toHexString(value & 0xff));
        }

        return stringBuilder.toString();
    }

    private Group findGroup(Long id) {
        return groupRepository.findById(id).orElseThrow();
    }
}
