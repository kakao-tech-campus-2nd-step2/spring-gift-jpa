package gift.validation.product;

import gift.constant.ErrorMessage;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = KakaoNotAllowedValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KakaoNotAllowed {

    String message() default ErrorMessage.PRODUCT_NAME_CONTAINS_KAKAO;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
