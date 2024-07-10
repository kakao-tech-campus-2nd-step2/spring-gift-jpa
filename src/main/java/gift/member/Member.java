package gift.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public class Member {

    @Id
    @Email(message = "This is not an email format")
    String email;

    @Column(nullable = false)
    String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(
        @Email(message = "This is not an email format") String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSamePassword(Member member) {
        return this.password.equals(member.getPassword());
    }

    public boolean isSamePassword(MemberDTO memberDTO) {
        return this.password.equals(memberDTO.getPassword());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member member) {
            return this.email.equals(member.getEmail())
                   && this.password.equals(member.getPassword());
        }
        return false;
    }
}
