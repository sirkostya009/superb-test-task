package ua.sirkostya009.superbtesttask.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AtLeastYearsOldValidator implements ConstraintValidator<AtLeastYearsOld, LocalDate> {
    @Value("${validation.min-age}")
    private int minimumAge;
    private boolean nullable;
    private String message;

    private final Environment environment;

    @Override
    public void initialize(AtLeastYearsOld constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
        message = constraintAnnotation.message();
        if (message.startsWith("{") && message.endsWith("}")) {
            message = environment.getProperty(message.substring(1, message.length() - 1));
        }
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        var result = (nullable && localDate == null) || LocalDate.now().getYear() - localDate.getYear() >= minimumAge;

        if (!result) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return result;
    }
}
