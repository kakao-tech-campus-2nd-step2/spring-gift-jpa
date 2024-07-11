package gift.util.mapper;

import gift.dto.user.UserRegisterRequest;
import gift.dto.user.UserResponse;
import gift.entity.User;

public class UserMapper {

    public static User toUser(UserRegisterRequest request) {
        return User.builder()
            .email(request.email())
            .password(request.password())
            .build();
    }

    public static UserResponse toResponse(String token) {
        return new UserResponse(token);
    }

}
