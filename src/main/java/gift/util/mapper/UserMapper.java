package gift.util.mapper;

import gift.dto.user.UserLoginRequest;
import gift.dto.user.UserRegisterRequest;
import gift.entity.User;

public class UserMapper {
    public static User toUser(UserLoginRequest request) {
        return new User(null, request.email(), request.password());
    }

    public static User toUser(UserRegisterRequest request) {
        return new User(null, request.email(), request.password());
    }

}
