package gift.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final Map<String, String> tokenStore = new HashMap<>();

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        storeToken(token, email);
        return token;
    }

    public String getEmailFromToken(String token) {
        return tokenStore.get(token);
    }

    public boolean validateToken(String token, String email) {
        return email.equals(getEmailFromToken(token));
    }

    public void invalidateToken(String token) {
        tokenStore.remove(token);
    }

    private void storeToken(String token, String email) {
        tokenStore.put(token, email);
    }
}
