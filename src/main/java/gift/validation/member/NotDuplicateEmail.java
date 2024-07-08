package gift.validation.member;

import gift.constant.ErrorMessage;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotDuplicateEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotDuplicateEmail {

    String message() default ErrorMessage.DUPLICATE_EMAIL;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
