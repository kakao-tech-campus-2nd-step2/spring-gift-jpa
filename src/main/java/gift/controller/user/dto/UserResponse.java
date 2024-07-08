package gift.controller.user.dto;

import gift.model.user.User;

public class UserResponse {

    public record LoginResponse(String token) {

        public static LoginResponse from(String token) {
            return new LoginResponse(token);
        }
    }

    public record InfoResponse(
        String email,
        String name
    ) {

        public static InfoResponse from(User user) {
            return new InfoResponse(user.getEmail(), user.getName());
        }
    }
}
