package gift.member.dto;

import gift.member.domain.Email;
import gift.member.domain.NickName;
import gift.member.domain.Password;

import java.util.Objects;

public record MemberRequestDTO(Email email, Password password, NickName nickName) {
    public MemberRequestDTO {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(nickName);
    }

    public MemberServiceDto toMemberServiceDto() {
        return new MemberServiceDto(null, email, password, nickName);
    }

    public MemberServiceDto toMemberServiceDto(Long id) {
        return new MemberServiceDto(id, email, password, nickName);
    }

}
