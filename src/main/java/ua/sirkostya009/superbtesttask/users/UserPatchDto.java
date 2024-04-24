package ua.sirkostya009.superbtesttask.users;

import jakarta.validation.constraints.Email;
import ua.sirkostya009.superbtesttask.validation.AtLeastYearsOld;
import ua.sirkostya009.superbtesttask.validation.NullOrNotBlank;

import java.time.LocalDate;

public record UserPatchDto(
        @Email(regexp = "^[\\w]+@[\\w]+.[a-zA-Z0-9]+$")
        String email,
        @NullOrNotBlank
        String firstName,
        @NullOrNotBlank
        String lastName,
        @AtLeastYearsOld(minimumAge = "{validation.min-age}", nullable = true)
        LocalDate birthDate,
        @NullOrNotBlank
        String address,
        @NullOrNotBlank
        String phoneNumber
) {
}
