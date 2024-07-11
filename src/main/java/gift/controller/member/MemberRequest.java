package gift.controller.member;

import gift.domain.Member;

public record MemberRequest(String email, String password){
    public static MemberRequest of(Member member) {
        return new MemberRequest(member.getEmail(), member.getPassword());
    }
    public static Member toMember(MemberRequest member) {
        return new Member(member.email(), member.password());
    }
}
