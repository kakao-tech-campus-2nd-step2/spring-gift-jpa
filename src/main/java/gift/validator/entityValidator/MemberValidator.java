package gift.validator.entityValidator;

import gift.dto.MemberDTO;
import java.util.regex.Pattern;

public class MemberValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    public void validate(MemberDTO memberDTO) {
        validateEmail(memberDTO.getEmail());
        validatePassword(memberDTO.getPassword());
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }
}
