package gift.domain.dto.request;

import gift.domain.entity.User;

public record UserRequest(String email, String password) {

    public static UserRequest of(User user) {
        return new UserRequest(user.email(), user.password());
    }
}
