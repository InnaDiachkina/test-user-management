package example.test.user.management.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidBirthDate, LocalDate> {
    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return date == null || date.isBefore(LocalDate.now());
    }
}
