package gift.domain.model;

public class UserResponseDto {

    private User user;
    private String token;

    public UserResponseDto(User user, String token) {
        this.user = user;
        this.token = token;
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> ec8458b (refacotr: UserRepository JPA 방식으로 리팩토링)

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
<<<<<<< HEAD
=======
>>>>>>> c87bd7d (refactor: schema.sql 삭제)
=======
>>>>>>> ec8458b (refacotr: UserRepository JPA 방식으로 리팩토링)
}
