package gift.security;

import gift.domain.Member;
import gift.validation.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final JwtTokenProvider jwtTokenProvider;
    public SecurityService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String generateJwtToken(Member member) {
        return jwtTokenProvider.generateToken(member);
    }
}
