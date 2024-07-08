package gift.validation.member;

import gift.repository.MemberRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotDuplicateEmailValidator implements ConstraintValidator<NotDuplicateEmail, String> {

    private final MemberRepository memberRepository;

    public NotDuplicateEmailValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !memberRepository.existsByEmail(value);
    }

}
