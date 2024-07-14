package gift.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public String getBearerTokenFromHeader(String header) {
        if (!header.toLowerCase().startsWith("bearer ")) {
            throw new RuntimeException("Bearer token not included in header");
        }
        return header.substring(7);
    }
}
