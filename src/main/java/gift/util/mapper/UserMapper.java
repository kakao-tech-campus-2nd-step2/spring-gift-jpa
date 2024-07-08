package gift.mapper;

import gift.dto.user.UserRequestDto;
import gift.entity.User;

public class UserMapper {
    public static User toUser(UserRequestDto userRequestDto) {
        return new User(null, userRequestDto.email(), userRequestDto.password());
    }

}
