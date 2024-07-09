package gift.user;

import jakarta.validation.constraints.*;

public record UserDTO(Long id,
                      @Email
                      @NotNull(message = "이메일을 입력하지 않았습니다.")
                      String email,
                      @NotNull(message = "비밀번호를 입력하지 않았습니다.")
                      String password) {

    public long getId() {return id;}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toUser() {
        return new User(email, password);
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword());
    }
}
