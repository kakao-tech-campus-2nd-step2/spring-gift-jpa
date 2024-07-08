package gift.annotation;

import gift.dto.MemberPasswordDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewPasswordsMatchValidator implements ConstraintValidator<NewPasswordsMatch, MemberPasswordDTO> {

    @Override
    public boolean isValid(MemberPasswordDTO memberPasswordDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (memberPasswordDTO == null) {
            return true;
        }
        if (memberPasswordDTO.newPassword1() == null || memberPasswordDTO.newPassword2() == null) {
            return true;
        }
        return memberPasswordDTO.newPassword1().equals(memberPasswordDTO.newPassword2());
    }
}
