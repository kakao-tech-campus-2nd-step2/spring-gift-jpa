package gift.domain.user.dto;

import gift.domain.user.entity.Role;
import gift.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(
    Long id,

    String name,

    @NotBlank(message = "이메일은 필수 입력 필드입니다.")
    @Email(message = "잘못된 이메일 형식입니다.")
    String email,

    @Size(min = 4, max = 20, message = "4-20자 사이의 비밀번호를 입력해주세요.")
    @Pattern(regexp = "[a-zA-z0-9!@^&\\-_]+", message = "영어 대소문자와 숫자, !,@,^,&,-,_ 만 사용 가능합니다.")
    String password,

    String role)
{
    public User toUser() {
        return new User(id, name, email, password, Role.USER);
    }
}
