package gift.member.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        public String email;
        public String password;

    public Member() {}

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public void email(String email) {
        this.email = email;
    }

    public String password() {
        return password;
    }

    public void password(String password) {
        this.password = password;
    }
}