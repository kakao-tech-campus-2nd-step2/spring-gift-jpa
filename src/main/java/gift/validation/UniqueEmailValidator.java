package gift.validation;

import gift.service.MemberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final MemberService memberService;

    public UniqueEmailValidator(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !memberService.hasDuplicatedEmail(value);
    }

}
