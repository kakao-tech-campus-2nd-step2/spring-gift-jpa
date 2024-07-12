package gift.domain.dto.request;

import gift.domain.entity.User;
import gift.global.util.HashUtil;

public record UserRequest(String email, String password) {

    public static UserRequest of(User user) {
        return new UserRequest(user.getEmail(), user.getPassword());
    }

    public User toEntity(String permission) {
        return new User(email, HashUtil.hashCode(password), permission);
    }
}
