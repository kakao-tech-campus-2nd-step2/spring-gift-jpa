package gift.model;

public class User {
    private Long id;
    private String email;
    private String password;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

    // 현재 이메일과 비밀번호가 일치하는 경우에만 이메일을 바꿀 수 있도록 함
    public void setEmail(String email, String currentPassword) {
        if (this.password.equals(currentPassword)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Current password is incorrect.");
        }
    }


    // 현재 이메일과 비밀번호가 일치하는 경우에만 비밀번호를 바꿀 수 있도록 함
    public void setPassword(String newPassword, String currentPassword) {
        if (this.password.equals(currentPassword)) {
            this.password = newPassword;
        } else {
            throw new IllegalArgumentException("Current password is incorrect.");
        }
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
