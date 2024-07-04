package gift.main.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final String secret;

    public AuthService(@Value("${spring.jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String createJwt(String email, String password) {

    }

    public boolean validateToken(String token, String email, String password) {

    }
}
