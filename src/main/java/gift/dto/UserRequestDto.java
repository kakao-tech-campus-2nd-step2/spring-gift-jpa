package gift.dto;

import gift.domain.User;

public class UserRequestDto {
    private String email;
    private String password;

    public User toEntity(){
        return new User(this.getEmail(), this.getPassword());
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
