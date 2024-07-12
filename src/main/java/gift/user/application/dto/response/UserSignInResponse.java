package gift.user.application.dto.response;

import gift.user.service.dto.UserSignInInfo;
import gift.user.service.dto.UserSignupInfo;

public record UserSignInResponse(
        String token
) {
    public static UserSignInResponse from(UserSignInInfo userSignInInfo) {
        return new UserSignInResponse(userSignInInfo.token());
    }

    public static UserSignInResponse from(UserSignupInfo userSignupInfo) {
        return new UserSignInResponse(userSignupInfo.token());
    }
}
