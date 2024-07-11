package gift.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String email;
    private String encryptedPw;

    public User() {}

    public User(String email, String encryptedPw) {
        this.email = email;
        this.encryptedPw = encryptedPw;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedPw() {
        return encryptedPw;
    }

    public void setEncryptedPw(String encryptedPw) {
        this.encryptedPw = encryptedPw;
    }
}
