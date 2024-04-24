package ua.sirkostya009.superbtesttask.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.sirkostya009.superbtesttask.validation.AtLeastYearsOld;
import ua.sirkostya009.superbtesttask.validation.NullOrNotBlank;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    @NotNull
    @Email(regexp = "^[\\w]+@[\\w]+.[a-zA-Z0-9]+$")
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @AtLeastYearsOld(minimumAge = "{validation.min-age}")
    private LocalDate birthDate;
    @NullOrNotBlank
    private String address;
    @NullOrNotBlank
    private String phoneNumber;
}
