package gift.model;

import org.springframework.stereotype.Component;

public class Member {
    private Long id;
    private String email;
    private String password;

    public Member(Long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member() {
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
}
