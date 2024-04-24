package ua.sirkostya009.superbtesttask.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {
    String message() default "{jakarta.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
