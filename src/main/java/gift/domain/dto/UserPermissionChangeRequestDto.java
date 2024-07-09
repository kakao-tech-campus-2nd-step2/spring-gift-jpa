package gift.domain.dto;

import gift.domain.entity.User;

public record UserPermissionChangeRequestDto(String email, String permission) {

    public static UserPermissionChangeRequestDto of(User user) {
        return new UserPermissionChangeRequestDto(user.email(), user.permission());
    }
}
