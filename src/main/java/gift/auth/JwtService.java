package gift.auth;

import gift.model.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void createToken(Member member, HttpServletResponse response) {
        response.setHeader("Authorization",jwtTokenProvider.generateToken(member));
    }

    public void createTokenMVC(Member member, HttpServletResponse response) {
        response.addCookie(new Cookie("jwtToken", jwtTokenProvider.generateToken(member) ));
    }
}
