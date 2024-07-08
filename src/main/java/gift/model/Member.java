package gift.model;

import javax.security.auth.login.CredentialNotFoundException;

public class Member {
    private Long id;
    private String email;
    private String password;
    private Role role;

    public Member() {
    }

    public Member(Long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // 회원가입 시 ROLE 기본값은 일반 유저로 한다.
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.ROLE_USER;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
