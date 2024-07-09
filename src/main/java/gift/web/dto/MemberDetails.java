package gift.web.dto;

import gift.domain.Member;
import gift.domain.vo.Email;

public class MemberDetails {

    private Long id;
    private Email email;

    public MemberDetails(Long id, Email email) {
        this.id = id;
        this.email = email;
    }

    public static MemberDetails from(Member member) {
        return new MemberDetails(member.getId(), member.getEmail());
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }
}
