package gift.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public record Member(
    @Id
    @Email(message = "This is not an email format")
    String email,

    @Column(nullable = false)
    String password) {

    public boolean isSamePassword(Member member) {
        return this.password.equals(member.password());
    }
}
