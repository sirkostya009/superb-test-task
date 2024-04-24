package ua.sirkostya009.superbtesttask.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        var user1 = new User(1, "example@email.com", "John", "Doe", LocalDate.parse("1999-04-20"), null, null);
        var user2 = new User(2, "test@email.com", "L", "D", LocalDate.parse("2002-01-10"), null, null);

        when(userRepository.getUsers(null, null)).thenReturn(List.of(user1, user2));

        when(userRepository.getUserById(1)).thenReturn(Optional.of(user1));
    }

    @Test
    public void testGetUsers_Invalid() throws Exception {
        mvc.perform(get("/users?from=2022-01-01&to=2021-01-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPatchUsers_Invalid() throws Exception {
        mvc.perform(patch("/users/1/address") // tests address validation
                        .content("""
                                {
                                    "address": "    "
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPatchUser_Successful() throws Exception {
        mvc.perform(patch("/users/1/email") // tests email validation
                        .content("""
                                {
                                    "email": "newemail@email.com"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful());

        assertThat(mvc.perform(get("/users/1"))
                .andReturn()
                .getResponse()
                .getContentAsString())
                .contains("newemail@email.com");
    }

    @Test
    public void testDeleteUser_Successful() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().is2xxSuccessful());

        when(userRepository.getUserById(1)).thenReturn(Optional.empty());

        mvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser_Invalid() throws Exception {
        mvc.perform(post("/users")
                .content("""
                        {
                            "email": "invalidemail",
                            "firstName": "John",
                            "lastName": "Doe",
                            "birthDate": "2023-04-20"
                        }
                        """)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/users")
                        .content("""
                        {
                            "email": "valid@email.com",
                            "firstName": "John",
                            "lastName": "Doe",
                            "birthDate": "2023-04-20"
                        }
                        """)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
