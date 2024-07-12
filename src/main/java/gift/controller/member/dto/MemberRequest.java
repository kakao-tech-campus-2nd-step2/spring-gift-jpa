package gift.controller.member.dto;

import gift.model.member.Member;
import gift.model.member.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberRequest {

    public record Register(
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name) {

        public Member toEntity() {
            return Member.create(null, email(), password(), name(), Role.USER);
        }
    }

    public record Login(
        @Email
        String email,
        @NotBlank
        String password) {

    }
}
