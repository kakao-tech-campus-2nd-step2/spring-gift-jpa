package gift.domain.dto;

import gift.domain.entity.User;

public record UserRequestDto(String email, String password) {

    public static UserRequestDto of(User user) {
        return new UserRequestDto(user.email(), user.password());
    }
}
