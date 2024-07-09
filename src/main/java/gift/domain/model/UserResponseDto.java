package gift.domain.model;

public class UserResponseDto {

    private User user;
    private String token;

    public UserResponseDto(User user, String token) {
        this.user = user;
        this.token = token;
    }
    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
