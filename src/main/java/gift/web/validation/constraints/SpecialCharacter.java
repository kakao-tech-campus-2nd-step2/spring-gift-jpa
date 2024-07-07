package gift.web.validation.constraints;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import gift.web.validation.validator.SpecialCharacterValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = SpecialCharacterValidator.class)
public @interface SpecialCharacter {

    String allowed();

    String message() default "{error.specialCharacter}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
