package gift.member;

import jakarta.validation.constraints.Email;

public record Member(
    @Email(message = "This is not an email format")
    String email,
    String password) {

    public boolean isSamePassword(Member member) {
        return this.password.equals(member.password());
    }
}
