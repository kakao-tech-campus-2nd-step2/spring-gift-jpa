package gift.util.mapper;

import gift.dto.user.UserLoginRequest;
import gift.dto.user.UserRegisterRequest;
import gift.entity.User;

public class UserMapper {
    public static User toUser(UserLoginRequest request) {
        return User.builder()
            .email(request.email())
            .password(request.password())
            .build();
    }

    public static User toUser(UserRegisterRequest request) {
        return User.builder()
            .email(request.email())
            .password(request.password())
            .build();
    }

}
