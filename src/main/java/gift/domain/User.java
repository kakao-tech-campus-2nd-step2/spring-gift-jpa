package gift.domain;

import jakarta.validation.constraints.NotBlank;

public class User {
    private Long id;
    private String name;
    @NotBlank(message = "이메일은 필수로 입력하셔야 합니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수로 입력하셔야 합니다.")
    private String password;
    private String role;
    public User() {}

    public User(String name, String email, String password, String role) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // 비밀번호 검증 메서드
    public boolean validatePassword(String rawPassword) {
        return this.password.equals(rawPassword);
    }
}
