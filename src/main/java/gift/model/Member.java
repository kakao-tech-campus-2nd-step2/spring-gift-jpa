package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Member {
    private Long id;

    @Email(message = "?΄λ©???μ???¬λ°λ₯΄μ? ?μ΅?λ€.")
    @NotEmpty(message = "?΄λ©?Όμ? ?μ ??ͺ©?λ??")
    private String email;

    @NotEmpty(message = "λΉλ?λ²νΈ???μ ??ͺ©?λ??")
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

