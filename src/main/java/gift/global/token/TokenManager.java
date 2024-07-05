package gift.global.token;

import gift.auth.domain.AuthInfo;

public interface TokenManager {
    String createAccessToken(AuthInfo authInfo);
}
