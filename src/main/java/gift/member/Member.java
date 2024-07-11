package gift.member;

import gift.token.MemberTokenDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    public Member() {
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(MemberDTO memberDTO) {
        this.email = memberDTO.getEmail();
        this.password = memberDTO.getPassword();
    }

    public boolean isSamePassword(Member member) {
        return this.password.equals(member.password);
    }

    public static Member fromMemberWithoutBody(MemberTokenDTO memberTokenDTO) {
        return new Member(memberTokenDTO.getEmail(), null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member member) {
            return this.email.equals(member.email);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
