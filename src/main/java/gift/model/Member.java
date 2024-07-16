package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Member {
    private Long id;

    @Email(message = "?´ë©”???•ì‹???¬ë°”ë¥´ì? ?ŠìŠµ?ˆë‹¤.")
    @NotEmpty(message = "?´ë©”?¼ì? ?„ìˆ˜ ??ª©?…ë‹ˆ??")
    private String email;

    @NotEmpty(message = "ë¹„ë?ë²ˆí˜¸???„ìˆ˜ ??ª©?…ë‹ˆ??")
    private String password;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

