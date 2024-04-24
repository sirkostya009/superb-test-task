package ua.sirkostya009.superbtesttask.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        userService.createUser(new User(
                -1,
                "boilebowski@email.com",
                "Boi",
                "Lebowski",
                LocalDate.of(1990, 1, 1),
                "1234",
                "1234567890"
        ));

        assertThat(userService.getUsers(null, null)).hasSize(3);
    }

    @Test
    public void testPatchField() {
        var dto = new UserPatchDto(null, null, null, null, null, null);

        userService.patchUserField(userService.getUserById(1), "address", dto);

        var user = userService.getUserById(1);
        assertThat(user.getAddress()).isNull();
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1);

        assertThat(userService.getUsers(null, null)).hasSize(2);
    }
}
