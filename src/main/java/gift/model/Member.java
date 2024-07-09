package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Member {
    private Long id;
    private String email;
    private String password;
    private String activeToken;

    public Member() {}

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member(Long id, String email, String password, String activeToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.activeToken = activeToken;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }
}
