package ua.sirkostya009.superbtesttask.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.sirkostya009.superbtesttask.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers(LocalDate from, LocalDate to) {
        return userRepository.getUsers(from, to);
    }

    public User getUserById(long id) {
        return userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.addUser(user);
    }

    public void patchUserField(User user, String field, UserPatchDto dto) {
        switch (field) {
            case "email" -> user.setEmail(Objects.requireNonNull(dto.email(), "email is null"));
            case "firstName" -> user.setFirstName(Objects.requireNonNull(dto.firstName(), "firstName is null"));
            case "lastName" -> user.setLastName(Objects.requireNonNull(dto.lastName(), "lastName is null"));
            case "birthDate" -> user.setBirthDate(Objects.requireNonNull(dto.birthDate(), "birthDate is null"));
            case "address" -> user.setAddress(dto.address());
            case "phoneNumber" -> user.setPhoneNumber(dto.phoneNumber());
            default -> throw new RuntimeException("Field " + field + " not found");
        }
    }

    public void updateUser(long id, User user) {
        userRepository.updateUser(id, user);
    }

    public void deleteUser(long id) {
        userRepository.deleteUser(id);
    }
}
