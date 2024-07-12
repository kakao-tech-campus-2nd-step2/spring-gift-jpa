package gift.domain.dto.request;

import gift.domain.entity.Member;

public record MemberPermissionChangeRequest(String email, String permission) {

    public static MemberPermissionChangeRequest of(Member member) {
        return new MemberPermissionChangeRequest(member.getEmail(), member.getPermission());
    }
}
