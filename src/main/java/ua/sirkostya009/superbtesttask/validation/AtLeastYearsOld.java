package ua.sirkostya009.superbtesttask.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastYearsOldValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastYearsOld {
    String minimumAge();

    boolean nullable() default false;

    String message() default "{validation.default-message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
