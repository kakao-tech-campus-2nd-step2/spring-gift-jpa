package gift.dto;

import gift.domain.User;

public class UserResponseDto {
    private String email;

    public UserResponseDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserResponseDto from(final User user){
        return new UserResponseDto(user.getEmail());
    }
}
