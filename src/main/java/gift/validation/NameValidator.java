package gift.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public void initialize(ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        // Check for 특수기호
        if (!name.matches("^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/]*$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("() [] + - & / 외의 특수기호는 불가합니다").addConstraintViolation();
            return false;
        }

        // Check for "카카오"
        if (name.contains("카카오")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("`카카오`가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능합니다").addConstraintViolation();
            return false;
        }

        return true;
    }
}
