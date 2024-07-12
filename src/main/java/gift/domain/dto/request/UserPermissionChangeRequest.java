package gift.domain.dto.request;

import gift.domain.entity.User;

public record UserPermissionChangeRequest(String email, String permission) {

    public static UserPermissionChangeRequest of(User user) {
        return new UserPermissionChangeRequest(user.email(), user.permission());
    }
}
