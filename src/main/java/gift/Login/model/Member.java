package gift.Login.model;

import java.util.UUID;

public class Member {
    private UUID id;
    private String email;
    private String password;

    // Constructors, Getters and Setters
    public Member() {
    }

    public Member(String email, String password) {
        this.id = UUID.randomUUID(); // id 값을 생성자에서 설정
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
