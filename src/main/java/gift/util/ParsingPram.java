package gift.util;

import gift.auth.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParsingPram {

    @Autowired
    private JwtToken jwtToken;
    private static final String AUTHORIZATION_HEADER = "Authorization";


    public Long getId(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        token = token.substring(7);
        return jwtToken.getId(token);
    }
}
