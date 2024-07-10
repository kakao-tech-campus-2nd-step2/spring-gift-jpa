package gift.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public class Member {

    @Id
    @Email(message = "This is not an email format")
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
