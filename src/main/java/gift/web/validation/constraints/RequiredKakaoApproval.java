package gift.web.validation.constraints;

import static gift.web.validation.exception.code.ErrorCode.DESCRIPTION.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import gift.web.validation.validator.KakaoApprovalValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = KakaoApprovalValidator.class)
public @interface RequiredKakaoApproval {

    String message() default KAKAO_APPROVAL_NEEDED;

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
