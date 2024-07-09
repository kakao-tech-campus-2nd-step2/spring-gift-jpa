package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class User{

    private Long id;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "유효한 이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    private String role;

    public User(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "이름을 입력하세요.") String getName() {
        return name;
    }

    public void setName(
        @NotBlank(message = "이름을 입력하세요.") String name) {
        this.name = name;
    }

    public @NotBlank(message = "이메일을 입력하세요.") @Email(message = "유효한 이메일을 입력하세요.") String getEmail() {
        return email;
    }

    public void setEmail(
        @NotBlank(message = "이메일을 입력하세요.") @Email(message = "유효한 이메일을 입력하세요.") String email) {
        this.email = email;
    }

    public @NotBlank(message = "비밀번호를 입력하세요.") String getPassword() {
        return password;
    }

    public void setPassword(
        @NotBlank(message = "비밀번호를 입력하세요.") String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
