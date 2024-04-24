package ua.sirkostya009.superbtesttask.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(required = false) LocalDate from,
                               @RequestParam(required = false) LocalDate to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("from is after to");
        }
        return userService.getUsers(from, to);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PatchMapping("/users/{id}/{fields}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchUser(@PathVariable long id,
                          @PathVariable List<String> fields,
                          @RequestBody @Valid UserPatchDto dto) {
        var user = userService.getUserById(id);

        fields.forEach(field -> userService.patchUserField(user, field, dto));

        userService.updateUser(id, user);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable long id,
                           @RequestBody @Valid User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, ?> handleRuntimeException(Exception e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, ?> handleValidationException(MethodArgumentNotValidException e) {
        return Map.of(
                "error", e.getBindingResult().getFieldError().getDefaultMessage(),
                "field", e.getBindingResult().getFieldError().getField()
        );
    }
}
