package ua.sirkostya009.superbtesttask.users;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private int idCount = 3;

    private final List<User> list = new ArrayList<>(List.of(
            new User(1, "example@email.com", "John", "Doe", LocalDate.parse("2003-04-13"), null, null),
            new User(2, "test@email.com", "Test", "Test", LocalDate.parse("1999-06-26"), null, null)
    ));

    public List<User> getUsers(LocalDate from, LocalDate to) {
        return list.stream()
                .filter(user -> from == null || user.getBirthDate().isAfter(from))
                .filter(user -> to   == null || user.getBirthDate().isBefore(to))
                .toList();
    }

    public Optional<User> getUserById(long id) {
        return list.stream().filter(user -> user.getId() == id).findFirst();
    }

    public User addUser(User user) {
        user.setId(idCount++);
        list.add(user);
        return user;
    }

    public void updateUser(long id, User user) {
        var found = list.stream().filter(u -> u.getId() == id).findFirst();
        found.ifPresent(u -> {
            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setBirthDate(user.getBirthDate());
            u.setAddress(user.getAddress());
            u.setPhoneNumber(user.getPhoneNumber());
        });
        if (found.isEmpty()) {
            addUser(user);
        }
    }

    public void deleteUser(long id) {
        list.removeIf(user -> user.getId() == id);
    }
}
