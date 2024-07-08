package gift.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NewPasswordsMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NewPasswordsMatch {

    String message() default "비밀번호 확인이 일치하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
