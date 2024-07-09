package gift.logout;

import gift.login.JwtTokenUtil;
import gift.member.LogoutTokenDao;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {
    private final LogoutTokenDao logoutTokenDao;

    public TokenValidator(LogoutTokenDao logoutTokenDao) {
        this.logoutTokenDao = logoutTokenDao;
    }

    public void validateToken(String token) throws IllegalAccessException {
        if(!logoutTokenDao.findToken(token)) {
            throw new IllegalAccessException("로그아웃되었습니다");
        }
    }
}
