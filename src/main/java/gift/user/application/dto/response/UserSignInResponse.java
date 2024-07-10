package gift.user.application.dto.response;

import gift.user.service.dto.UserSignInInfos;
import gift.user.service.dto.UserSignupInfos;

public record UserSignInResponse(
        String token
) {
    public static UserSignInResponse from(UserSignInInfos userSignInInfos) {
        return new UserSignInResponse(userSignInInfos.token());
    }

    public static UserSignInResponse from(UserSignupInfos userSignupInfos) {
        return new UserSignInResponse(userSignupInfos.token());
    }
}
